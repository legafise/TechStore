package by.lashkevich.logic.service;

import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.entity.Entity;

import java.util.List;

public interface Service<T extends Entity> {
    void setTransaction(Transaction transaction);
    void setTransactionFactory(TransactionFactory factory);
    void closeTransaction() throws ServiceException;
    List<T> findAll() throws ServiceException;
    T findById(String id);
    boolean add(T entity) throws ServiceException;
    boolean removeById(String id);
    boolean update(T entity);
}
