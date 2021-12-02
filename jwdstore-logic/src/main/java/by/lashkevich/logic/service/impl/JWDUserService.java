package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionManager;
import by.lashkevich.logic.entity.Basket;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.logic.service.checker.UserAddingDuplicationChecker;
import by.lashkevich.logic.service.checker.UserUpdatingDuplicationChecker;
import by.lashkevich.logic.service.validator.UserValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class JWDUserService implements UserService {
    private static final String EMPTY_PICTURE_NAME = "";
    private static final String STANDARD_USER_PICTURE_NAME = "default.jpg";
    private static final String NONEXISTENT_USER_MESSAGE = "Nonexistent user data was received";
    private static final String INVALID_USER_MESSAGE = "Invalid user was received";
    private static final String INVALID_GOOD_ID_MESSAGE = "Invalid good id was received";
    private static final String GOOD_THERE_IS_NOT_MESSAGE = "The good there isn't in basket";
    private static final String INVALID_GOOD_QUANTITY = "Invalid good quantity %d";
    private final UserDao userDao;
    private final Predicate<User> userValidator;
    private final Predicate<User> userAddingDuplicationChecker;
    private final Predicate<User> userUpdatingDuplicationChecker;
    private final TransactionManager transactionManager;

    public JWDUserService() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
        userValidator = new UserValidator();
        userAddingDuplicationChecker = new UserAddingDuplicationChecker();
        userUpdatingDuplicationChecker = new UserUpdatingDuplicationChecker();
        transactionManager = TransactionManager.getInstance();
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findUserById(String id) throws ServiceException {
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
    public boolean addUser(User user) throws ServiceException {
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
    public boolean removeUserById(String id) throws ServiceException {
        try {
            return userDao.removeById(Long.parseLong(id));
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
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
    public Optional<Basket> findBasketByUserId(String userId) throws ServiceException {
        try {
            return userDao.findBasketByUserId(Long.parseLong(userId));
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

    @Override
    public boolean upBalance(String amount, String userId) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Optional<User> userOptional = userDao.findById(Long.valueOf(userId));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setBalance(user.getBalance().add(new BigDecimal(amount)));
                boolean result = userDao.update(user);
                if (result) {
                    transactionManager.commit(transaction);
                } else {
                    transactionManager.rollback(transaction);
                }

                return result;
            }

            transactionManager.rollback(transaction);
            throw new ServiceException(INVALID_USER_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e);
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean addGoodInBasket(String userId, String goodId) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Long longUserId = Long.parseLong(userId);
            long longGoodId = Long.parseLong(goodId);
            boolean goodAddingResult;

            Optional<Basket> basketOptional = userDao.findBasketByUserId(longUserId);
            if (!basketOptional.isPresent() || basketOptional.get().getGoods().entrySet().stream()
                    .noneMatch(entry -> entry.getKey().getId() == longGoodId)) {
                goodAddingResult = userDao.addGoodInBasket(longUserId, longGoodId);
            } else {
                goodAddingResult = userDao.changeGoodQuantity(longUserId, longGoodId, basketOptional.get()
                        .getGoods().entrySet().stream()
                        .filter(entry -> entry.getKey().getId() == longGoodId)
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .orElseThrow(() -> new ServiceException(INVALID_GOOD_ID_MESSAGE)) + 1);
            }

            if (goodAddingResult) {
                transactionManager.commit(transaction);
            } else {
                transactionManager.rollback(transaction);
            }

            return goodAddingResult;
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e);
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean removeGoodFromBasket(String userId, String goodId) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Long longUserId = Long.parseLong(userId);
            long longGoodId = Long.parseLong(goodId);

            Optional<Basket> basketOptional = userDao.findBasketByUserId(longUserId);
            if (!basketOptional.isPresent() || basketOptional.get().getGoods().entrySet().stream()
                    .noneMatch(entry -> entry.getKey().getId() == longGoodId)) {
                throw new ServiceException(GOOD_THERE_IS_NOT_MESSAGE);
            }

            boolean goodRemovingResult = userDao.removeGoodFromBasket(longUserId, longGoodId);

            if (goodRemovingResult) {
                transactionManager.commit(transaction);
            } else {
                transactionManager.rollback(transaction);
            }

            return goodRemovingResult;
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e);
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean changeGoodQuantityInBasket(String userId, String goodId, String quantity) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Long longUserId = Long.parseLong(userId);
            long longGoodId = Long.parseLong(goodId);
            int intQuantity = Integer.parseInt(quantity);

            if (intQuantity < 1) {
                throw new ServiceException(String.format(INVALID_GOOD_QUANTITY, intQuantity));
            }

            Optional<Basket> basketOptional = userDao.findBasketByUserId(longUserId);
            if (!basketOptional.isPresent() || basketOptional.get().getGoods().entrySet().stream()
                    .noneMatch(entry -> entry.getKey().getId() == longGoodId)) {
                throw new ServiceException(GOOD_THERE_IS_NOT_MESSAGE);
            }

            boolean goodUpdatingResult = userDao.changeGoodQuantity(longUserId, longGoodId, intQuantity);

            if (goodUpdatingResult) {
                transactionManager.commit(transaction);
            } else {
                transactionManager.rollback(transaction);
            }

            return goodUpdatingResult;
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e);
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    private void setStandardPicture(User user) {
        if (user.getProfilePictureName().equals(EMPTY_PICTURE_NAME)) {
            user.setProfilePictureName(STANDARD_USER_PICTURE_NAME);
        }
    }
}
