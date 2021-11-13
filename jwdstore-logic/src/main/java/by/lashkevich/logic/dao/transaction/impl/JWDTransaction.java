package by.lashkevich.logic.dao.transaction.impl;

import by.lashkevich.logic.dao.BaseDao;
import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoType;
import by.lashkevich.logic.dao.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class JWDTransaction implements Transaction {
    private Connection connection;

    public JWDTransaction(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends BaseDao<?, ?>> T createDao(DaoType type) {
        T dao = (T) type.createDao();
        dao.setConnection(connection);
        return dao;
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
}
