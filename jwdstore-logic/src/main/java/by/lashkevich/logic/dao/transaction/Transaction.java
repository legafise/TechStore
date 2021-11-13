package by.lashkevich.logic.dao.transaction;

import by.lashkevich.logic.dao.BaseDao;
import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoType;

public interface Transaction {
    <T extends BaseDao<?, ?>> T createDao(DaoType type);
    void commit() throws DaoException;
    void rollback() throws DaoException;
}
