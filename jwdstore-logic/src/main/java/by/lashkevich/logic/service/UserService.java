package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.util.Optional;

public interface UserService extends Service<User> {
    Basket findBasketByUserId(String userId) throws ServiceException;
    Optional<User> findUserByEmail(String email) throws ServiceException;
    Optional<User> findUserByLogin(String login) throws ServiceException;
    boolean removeBasketByUserId(String userId) throws ServiceException;
}
