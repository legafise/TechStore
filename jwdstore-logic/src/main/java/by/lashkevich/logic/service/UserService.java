package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service.
 * @author Roman Lashkevich
 */
public interface UserService extends Service {
    /**
     * Find all users list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAllUsers() throws ServiceException;

    /**
     * Find user by id user.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    User findUserById(String id) throws ServiceException;

    /**
     * Add user boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addUser(User user) throws ServiceException;

    /**
     * Remove user by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeUserById(String id) throws ServiceException;

    /**
     * Update user boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUser(User user) throws ServiceException;

    /**
     * Find basket by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Basket> findBasketByUserId(String userId) throws ServiceException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByLogin(String login) throws ServiceException;

    /**
     * Remove basket by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeBasketByUserId(String userId) throws ServiceException;

    /**
     * Up balance boolean.
     *
     * @param amount the amount
     * @param userId the user id
     * @return the boolean
     */
    boolean upBalance(String amount, String userId);

    /**
     * Add good in basket boolean.
     *
     * @param userId the user id
     * @param goodId the good id
     * @return the boolean
     */
    boolean addGoodInBasket(String userId, String goodId);

    /**
     * Remove good from basket boolean.
     *
     * @param userId the user id
     * @param goodId the good id
     * @return the boolean
     */
    boolean removeGoodFromBasket(String userId, String goodId);

    /**
     * Change good quantity in basket boolean.
     *
     * @param userId   the user id
     * @param goodId   the good id
     * @param quantity the quantity
     * @return the boolean
     */
    boolean changeGoodQuantityInBasket(String userId, String goodId, String quantity);
}
