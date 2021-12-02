package by.lashkevich.logic.dao.transaction;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.dao.transaction.impl.JWDTransaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionManager {
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static final AtomicBoolean IS_INSTANCE_CREATED = new AtomicBoolean(false);
    private static TransactionManager instance;
    private static final Map<String, Integer> TRANSACTION_USERS = new HashMap<>();

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (!IS_INSTANCE_CREATED.get()) {
            try {
                INSTANCE_LOCK.lock();
                if (instance == null) {
                    instance = new TransactionManager();
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
            Connection connection;
            if (TRANSACTION_USERS.containsKey(Thread.currentThread().getName())) {
                TRANSACTION_USERS.put(Thread.currentThread().getName(),
                        TRANSACTION_USERS.get(Thread.currentThread().getName()) + 1);
                connection = ConnectionPool.getInstance().acquireConnection();
            } else {
                TRANSACTION_USERS.put(Thread.currentThread().getName(), 1);
                connection = ConnectionPool.getInstance().reserveTransactionalConnection();
                connection.setAutoCommit(false);
            }

            return new JWDTransaction(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void closeTransaction(Transaction transaction) {
        Integer transactionUsedNumber = TransactionManager.TRANSACTION_USERS.get(Thread.currentThread().getName());
        if (transactionUsedNumber == 1) {
            TransactionManager.TRANSACTION_USERS.remove(Thread.currentThread().getName());
            transaction.closeTransaction();
        } else {
            TransactionManager.TRANSACTION_USERS.put(Thread.currentThread().getName(), transactionUsedNumber - 1);
        }
    }

    public void rollback(Transaction transaction) {
        if (TransactionManager.TRANSACTION_USERS.get(Thread.currentThread().getName()) == 1) {
            transaction.rollback();
        }
    }

    public void commit(Transaction transaction) {
        if (TransactionManager.TRANSACTION_USERS.get(Thread.currentThread().getName()) == 1) {
            transaction.commit();
        }
    }
}