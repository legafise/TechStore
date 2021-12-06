package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <K> the type parameter
 * @param <T> the type parameter
 * @author Roman Lashkevich
 */
public interface BaseDao<K, T extends Entity> {
    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(K id) throws DaoException;

    /**
     * Add boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(T entity) throws DaoException;

    /**
     * Remove by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeById(K id) throws DaoException;

    /**
     * Update boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(T entity) throws DaoException;
}
