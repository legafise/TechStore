package by.lashkevich.logic.dao.transaction;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.dao.transaction.impl.JWDTransaction;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactory {
    private final Connection connection;
    private boolean isAutoCommitActive;

    public TransactionFactory() throws DaoException {
        try {
            connection = ConnectionPool.getInstance().acquireConnection();
            connection.setAutoCommit(false);
            isAutoCommitActive = false;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public boolean isAutoCommitActive() {
        return isAutoCommitActive;
    }

    public void changeAutoCommitStatus(boolean status) throws DaoException {
        try {
            connection.setAutoCommit(status);
            isAutoCommitActive = status;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public Transaction createTransaction() {
        return new JWDTransaction(connection);
    }

    public void closeTransaction() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }
}