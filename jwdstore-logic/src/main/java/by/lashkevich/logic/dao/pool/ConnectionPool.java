package by.lashkevich.logic.dao.pool;

import by.lashkevich.logic.dao.reader.impl.DataBasePropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Object that contains all created connections and manages them.
 *
 * @author Roman Lashkevich
 */
public class ConnectionPool {
    private static final AtomicBoolean IS_INSTANCE_CREATED = new AtomicBoolean(false);
    private static final String CONNECTION_IS_NULL_ENTER_MESSAGE = "Connection cannot be null";
    private static final String INCORRECT_CONNECTION_ENTER_MESSAGE = "Return connection does not exist";
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final Condition CONNECTION_CONDITION = CONNECTION_LOCK.newCondition();
    private static ConnectionPool instance;
    private Map<String, Connection> busyTransactionalConnections;
    private Deque<Connection> freeConnections;
    private Deque<Connection> busyConnections;
    private DataBasePropertiesReader propertiesReader;

    private ConnectionPool() {
        freeConnections = new ArrayDeque<>();
        busyConnections = new ArrayDeque<>();
        propertiesReader = new DataBasePropertiesReader();
        busyTransactionalConnections = new HashMap<>();
    }

    /**
     * Sets properties reader.
     *
     * @param propertiesReader the properties reader
     */
    public void setPropertiesReader(DataBasePropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    /**
     * Gets free connections size.
     *
     * @return the free connections size
     */
    public int getFreeConnectionsSize() {
        return freeConnections.size();
    }

    /**
     * Gets busy connections size.
     *
     * @return the busy connections size
     */
    public int getBusyConnectionsSize() {
        return busyConnections.size();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        if (!IS_INSTANCE_CREATED.get()) {
            try {
                INSTANCE_LOCK.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    IS_INSTANCE_CREATED.set(true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return instance;
    }

    /**
     * Initialize connection pool.
     *
     * @param connectionsNumber the connections number
     * @throws ConnectionPoolException the connection pool exception
     */
    public void initializeConnectionPool(int connectionsNumber) throws ConnectionPoolException {
        try {
            CONNECTION_LOCK.lock();
            Class.forName(propertiesReader.readDriverName());

            for (int i = 0; i < connectionsNumber; i++) {
                freeConnections.push(new ProxyConnection(DriverManager.getConnection(propertiesReader.readUrl(),
                        propertiesReader.readProperties())));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ConnectionPoolException(e);
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    /**
     * Acquire connection connection.
     *
     * @return the connection
     * @throws ConnectionPoolException the connection pool exception
     */
    public Connection acquireConnection() throws ConnectionPoolException {
        try {
            CONNECTION_LOCK.lock();
            if (busyTransactionalConnections.containsKey(Thread.currentThread().getName())) {
                return busyTransactionalConnections.get(Thread.currentThread().getName());
            }

            Connection connection = takeConnection();
            busyConnections.push(connection);
            return connection;
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    /**
     * Reserve transactional connection connection.
     *
     * @return the connection
     * @throws ConnectionPoolException the connection pool exception
     */
    public Connection reserveTransactionalConnection() throws ConnectionPoolException {
        Connection connection = takeConnection();
        busyTransactionalConnections.put(Thread.currentThread().getName(), connection);
        busyConnections.add(connection);
        return connection;
    }

    /**
     * Put back connection.
     *
     * @param connection the connection
     * @throws ConnectionPoolException the connection pool exception
     */
    public void putBackConnection(Connection connection) throws ConnectionPoolException {
        if (connection == null) {
            throw new ConnectionPoolException(CONNECTION_IS_NULL_ENTER_MESSAGE);
        }

        try {
            CONNECTION_LOCK.lock();
            if (!busyTransactionalConnections.containsKey(Thread.currentThread().getName())) {
                if (busyConnections.remove(connection)) {
                    freeConnections.add(connection);
                    CONNECTION_CONDITION.signal();
                } else {
                    throw new ConnectionPoolException(INCORRECT_CONNECTION_ENTER_MESSAGE);
                }
            }
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    /**
     * Close connections.
     *
     * @throws ConnectionPoolException the connection pool exception
     */
    public void closeConnections() throws ConnectionPoolException {
        try {
            CONNECTION_LOCK.lock();
            for (Connection connection : busyConnections) {
                ProxyConnection proxyConnection = (ProxyConnection) connection;
                proxyConnection.getConnection().close();
            }

            for (Connection connection : freeConnections) {
                ProxyConnection proxyConnection = (ProxyConnection) connection;
                proxyConnection.getConnection().close();
            }

            for (Connection connection : busyTransactionalConnections.values()) {
                ProxyConnection proxyConnection = (ProxyConnection) connection;
                proxyConnection.getConnection().close();
            }

            freeConnections = new ArrayDeque<>();
            busyConnections = new ArrayDeque<>();
            busyTransactionalConnections = new HashMap<>();
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }

    /**
     * Put back transactional connection.
     */
    public void putBackTransactionalConnection() {
        Connection transactionalConnection = busyTransactionalConnections.remove(Thread.currentThread().getName());
        putBackConnection(transactionalConnection);
    }

    private Connection takeConnection() {
        try {
            CONNECTION_LOCK.lock();
            if (freeConnections.isEmpty()) {
                CONNECTION_LOCK.unlock();
                CONNECTION_CONDITION.await();
                CONNECTION_LOCK.lock();
            }

            return freeConnections.poll();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        } finally {
            CONNECTION_LOCK.unlock();
        }
    }
}
