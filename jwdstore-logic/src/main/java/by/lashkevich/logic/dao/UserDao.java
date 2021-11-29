package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    Optional<User> findByEmail(String email) throws DaoException;
    Optional<User> findByLogin(String login) throws DaoException;
    boolean clearBasketByUserId(Long userId) throws DaoException;
    Optional<Basket> findBasketByUserId(Long userId) throws DaoException;
    boolean addGoodInBasket(Long userId, Long goodId) throws DaoException;
    boolean removeGoodFromBasket(Long userId, Long goodId) throws DaoException;
    boolean changeGoodQuantity(Long userId, Long goodId, Integer goodQuantity) throws DaoException;
}
