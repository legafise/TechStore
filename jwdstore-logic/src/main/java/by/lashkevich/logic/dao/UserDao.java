package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    boolean addGoodInBasket(Long goodId, Long userId, int quantity) throws DaoException;
    boolean clearBasketByUserId(Long userId) throws DaoException;
    boolean removeGoodFromBasket(Long goodId, Long userId, int quantity);
    Optional<Basket> findBasketByUserId(Long userId) throws DaoException;
}
