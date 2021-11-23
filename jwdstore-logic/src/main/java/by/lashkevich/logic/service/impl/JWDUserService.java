package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.logic.service.checker.UserAddingDuplicationChecker;
import by.lashkevich.logic.service.checker.UserUpdatingDuplicationChecker;
import by.lashkevich.logic.service.validator.UserValidator;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JWDUserService implements UserService {
    private static final String EMPTY_PICTURE_NAME = "";
    private static final String STANDARD_USER_PICTURE_NAME = "default.jpg";
    private static final String NONEXISTENT_USER_MESSAGE = "Nonexistent user data was received";
    private static final String INVALID_USER_MESSAGE = "Invalid user was received";
    private static final String USERS_BASKET_IS_EMPTY_MESSAGE = "The user dont have goods" +
            " in basket or user id is invalid";
    private final UserDao userDao;
    private final Predicate<User> userValidator;
    private final Predicate<User> userAddingDuplicationChecker;
    private final Predicate<User> userUpdatingDuplicationChecker;

    public JWDUserService() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
        userValidator = new UserValidator();
        userAddingDuplicationChecker = new UserAddingDuplicationChecker();
        userUpdatingDuplicationChecker = new UserUpdatingDuplicationChecker();
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findById(String id) throws ServiceException {
        try {
            Optional<User> userOptional = userDao.findById(Long.parseLong(id));

            if (userOptional.isPresent()) {
                return userOptional.get();
            }

            throw new ServiceException(NONEXISTENT_USER_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(User user) throws ServiceException {
        try {
            if (userValidator.test(user) && userAddingDuplicationChecker.test(user)) {
                setStandardPicture(user);
                return userDao.add(user);
            }

            throw new ServiceException(INVALID_USER_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeById(String id) throws ServiceException {
        try {
            return userDao.removeById(Long.parseLong(id));
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try {
            if (userValidator.test(user) && userUpdatingDuplicationChecker.test(user) && user.getId() != 0) {
                setStandardPicture(user);
                return userDao.add(user);
            }

            throw new ServiceException(INVALID_USER_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Basket findBasketByUserId(String userId) throws ServiceException {
        try {
            Optional<Basket> basketOptional = userDao.findBasketByUserId(Long.parseLong(userId));

            if (basketOptional.isPresent()) {
                return basketOptional.get();
            }

            throw new ServiceException(USERS_BASKET_IS_EMPTY_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeBasketByUserId(String userId) throws ServiceException {
        try {
            return userDao.clearBasketByUserId(Long.parseLong(userId));
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }
    
    private void setStandardPicture(User user) {
        if (user.getProfilePictureName().equals(EMPTY_PICTURE_NAME)) {
            user.setProfilePictureName(STANDARD_USER_PICTURE_NAME);
        }
    }
}
