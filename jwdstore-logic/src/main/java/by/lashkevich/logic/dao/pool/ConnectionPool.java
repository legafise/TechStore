package by.lashkevich.logic.dao.pool;

import by.lashkevich.logic.dao.reader.DataBasePropertiesReader;
import by.lashkevich.logic.dao.reader.PropertiesReaderException;
import by.lashkevich.logic.dao.reader.impl.ProdDataBasePropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final AtomicBoolean INSTANCE_CREATED = new AtomicBoolean(false);
    private static final String CONNECTION_IS_NULL_ENTER_MESSAGE = "Connection cannot be null";
    private static final String INCORRECT_CONNECTION_ENTER_MESSAGE = "Return connection does not exist";
    private static final String CONNECTIONS_NOT_CREATED_ENTER_MESSAGE = "Connections not created";
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final Condition CONNECTION_CONDITION = CONNECTION_LOCK.newCondition();
    private static ConnectionPool instance;
    private Deque<Connection> freeConnections;
    private Deque<Connection> busyConnections;
    private DataBasePropertiesReader propertiesReader;

    private ConnectionPool() {
        freeConnections = new ArrayDeque<>();
        busyConnections = new ArrayDeque<>();
        propertiesReader = new ProdDataBasePropertiesReader();
    }

    public void setPropertiesReader(DataBasePropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public int getFreeConnectionsSize() {
        return freeConnections.size();
    }

    public int getBusyConnectionsSize() {
        return busyConnections.size();
    }

    public static ConnectionPool getInstance() {
        if (!INSTANCE_CREATED.get()) {
            try {
                INSTANCE_LOCK.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    INSTANCE_CREATED.set(true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return instance;
    }

    public void initializeConnectionPool(int connectionsNumber) throws ConnectionPoolException {
        try {
            closeConnections();
            CONNECTION_LOCK.lock();
            Class.forName(propertiesReader.readDriverName());

            for (int i = 0; i < connectionsNumber; i++) {
                freeConnections.push(new ProxyConnection(DriverManager.getConnection(propertiesReader.readUrl(),
                        propertiesReader.readProperties())));
            }
        } catch (ClassNotFoundException | SQLException | PropertiesReaderException e) {
            throw new ConnectionPoolException(e.getMessage());
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    public Connection acquireConnection() throws ConnectionPoolException {
        try {
            CONNECTION_LOCK.lock();
            if (!freeConnections.isEmpty() || !busyConnections.isEmpty()) {
                if (freeConnections.isEmpty()) {
                    CONNECTION_CONDITION.await();
                }
                Connection connection = freeConnections.poll();
                busyConnections.push(connection);
                return connection;
            } else {
                throw new ConnectionPoolException(CONNECTIONS_NOT_CREATED_ENTER_MESSAGE);
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e.getMessage());
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    public void putBackConnection(Connection connection) throws ConnectionPoolException {
        if (connection == null) {
            throw new ConnectionPoolException(CONNECTION_IS_NULL_ENTER_MESSAGE);
        }

        try {
            CONNECTION_LOCK.lock();
            if (busyConnections.remove(connection)) {
                freeConnections.add(connection);
                if (freeConnections.size() >= 1) {
                    CONNECTION_CONDITION.signal();
                }
            } else {
                throw new ConnectionPoolException(INCORRECT_CONNECTION_ENTER_MESSAGE);
            }
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    public void closeConnections() throws ConnectionPoolException {
        try {
            CONNECTION_LOCK.lock();
            TimeUnit.SECONDS.sleep(1);
            for (Connection connection : freeConnections) {
                ProxyConnection proxyConnection = (ProxyConnection) connection;
                proxyConnection.getConnection().close();

                freeConnections = new ArrayDeque<>();
                busyConnections = new ArrayDeque<>();
            }
        } catch (InterruptedException | SQLException e) {
            throw new ConnectionPoolException(e.getMessage());
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }
}
