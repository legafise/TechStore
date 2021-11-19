package by.lashkevich.logic.dao.transaction;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.dao.transaction.impl.JWDTransaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionFactory {
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final AtomicBoolean IS_INSTANCE_CREATED = new AtomicBoolean(false);
    private static TransactionFactory instance;

    private TransactionFactory() {
    }

    public static TransactionFactory getInstance() {
        if (!IS_INSTANCE_CREATED.get()) {
            try {
                INSTANCE_LOCK.lock();
                if (instance == null) {
                    instance = new TransactionFactory();
                    IS_INSTANCE_CREATED.set(true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }

        return instance;
    }

    public Transaction createTransaction() throws DaoException {
        try {
            Connection connection = ConnectionPool.getInstance().reserveTransactionalConnection();
            connection.setAutoCommit(false);
            return new JWDTransaction(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}