package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

public interface UserService extends Service<User> {
    Basket findBasketByUserId(String userId) throws ServiceException;
    boolean removeBasketByUserId(String userId) throws ServiceException;
}
