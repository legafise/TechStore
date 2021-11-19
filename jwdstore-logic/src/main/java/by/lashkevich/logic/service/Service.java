package by.lashkevich.logic.service;

import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.entity.Entity;

import java.util.List;

public interface Service<T extends Entity> {
    List<T> findAll() throws ServiceException;
    T findById(String id) throws ServiceException;
    boolean add(T entity) throws ServiceException;
    boolean removeById(String id) throws ServiceException;
    boolean update(T entity) throws ServiceException;
}
