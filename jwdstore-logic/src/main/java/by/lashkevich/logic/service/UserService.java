package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service {
    List<User> findAllUsers() throws ServiceException;

    User findUserById(String id) throws ServiceException;

    boolean addUser(User user) throws ServiceException;

    boolean removeUserById(String id) throws ServiceException;

    boolean updateUser(User user) throws ServiceException;

    Basket findBasketByUserId(String userId) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    Optional<User> findUserByLogin(String login) throws ServiceException;

    boolean removeBasketByUserId(String userId) throws ServiceException;
}
