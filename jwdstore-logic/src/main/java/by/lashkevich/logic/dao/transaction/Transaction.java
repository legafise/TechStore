package by.lashkevich.logic.dao.transaction;

import by.lashkevich.logic.dao.DaoException;

public interface Transaction {
    void commit() throws DaoException;
    void rollback() throws DaoException;
    void closeTransaction() throws DaoException;

    // TODO: 18.11.2021 Уровни изоляций транзакций
    // TODO: 18.11.2021 ASID
    // TODO: 18.11.2021 Dirty Read, Repeatable Read etc 
}
