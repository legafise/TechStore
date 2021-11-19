package by.lashkevich.logic.dao.transaction.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.dao.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class JWDTransaction implements Transaction {
    private final Connection connection;

    public JWDTransaction(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public void closeTransaction() {
        ConnectionPool.getInstance().putBackTransactionalConnection();
    }
}