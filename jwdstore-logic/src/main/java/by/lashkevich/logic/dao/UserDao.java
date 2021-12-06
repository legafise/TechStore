package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.util.Optional;

/**
 * The interface User dao.
 * @author Roman Lashkevich
 * @see BaseDao
 */
public interface UserDao extends BaseDao<Long, User> {
    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Clear basket by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean clearBasketByUserId(Long userId) throws DaoException;

    /**
     * Find basket by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Basket> findBasketByUserId(Long userId) throws DaoException;

    /**
     * Add good in basket boolean.
     *
     * @param userId the user id
     * @param goodId the good id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean addGoodInBasket(Long userId, Long goodId) throws DaoException;

    /**
     * Remove good from basket boolean.
     *
     * @param userId the user id
     * @param goodId the good id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean removeGoodFromBasket(Long userId, Long goodId) throws DaoException;

    /**
     * Change good quantity boolean.
     *
     * @param userId       the user id
     * @param goodId       the good id
     * @param goodQuantity the good quantity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean changeGoodQuantity(Long userId, Long goodId, Integer goodQuantity) throws DaoException;
}
