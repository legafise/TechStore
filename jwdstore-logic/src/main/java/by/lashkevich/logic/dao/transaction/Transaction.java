package by.lashkevich.logic.dao.transaction;

import by.lashkevich.logic.dao.DaoException;

/**
 * The interface Transaction.
 * @author Roman Lashkevich
 */
public interface Transaction {
    /**
     * Commit.
     *
     * @throws DaoException the dao exception
     */
    void commit() throws DaoException;

    /**
     * Rollback.
     *
     * @throws DaoException the dao exception
     */
    void rollback() throws DaoException;

    /**
     * Close transaction.
     *
     * @throws DaoException the dao exception
     */
    void closeTransaction() throws DaoException;
}
