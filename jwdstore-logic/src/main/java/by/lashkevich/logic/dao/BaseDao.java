package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends Entity> {
    List<T> findAll() throws DaoException;
    Optional<T> findById(K id) throws DaoException;
    boolean add(T entity) throws DaoException;
    boolean removeById(K id) throws DaoException;
    boolean update(T entity) throws DaoException;
}
