package by.lashkevich.logic.service.impl;

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
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Jwd user service.
 *
 * @author Roman Lashkevich
 * @see UserService
 */
public class JWDUserService implements UserService {
    private static final String EMPTY_PICTURE_NAME = "";
    private static final String STANDARD_USER_PICTURE_NAME = "default.jpg";
    private static final String NONEXISTENT_USER_MESSAGE = "Nonexistent user";
    private static final String INVALID_USER_MESSAGE = "Invalid user data";
    private static final String INVALID_GOOD_ID_MESSAGE = "Invalid good id was received";
    private static final String GOOD_THERE_IS_NOT_MESSAGE = "The good there isn't in basket";
    private static final String INVALID_GOOD_QUANTITY = "Invalid good quantity %d";
    private final TransactionManager transactionManager;
    private UserValidator userValidator;
    private UserAddingDuplicationChecker userAddingDuplicationChecker;
    private UserUpdatingDuplicationChecker userUpdatingDuplicationChecker;
    private UserDao userDao;

    public JWDUserService() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
        userValidator = new UserValidator();
        userAddingDuplicationChecker = new UserAddingDuplicationChecker();
        userUpdatingDuplicationChecker = new UserUpdatingDuplicationChecker();
        transactionManager = TransactionManager.getInstance();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public void setUserAddingDuplicationChecker(UserAddingDuplicationChecker userAddingDuplicationChecker) {
        this.userAddingDuplicationChecker = userAddingDuplicationChecker;
    }

    public void setUserUpdatingDuplicationChecker(UserUpdatingDuplicationChecker userUpdatingDuplicationChecker) {
        this.userUpdatingDuplicationChecker = userUpdatingDuplicationChecker;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserById(String id) {
        Optional<User> userOptional = userDao.findById(Long.parseLong(id));

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new ServiceException(NONEXISTENT_USER_MESSAGE);
    }

    @Override
    public boolean addUser(User user) {
        setStandardPicture(user);
        if (userValidator.validate(user)) {
            if (userAddingDuplicationChecker.check(user)) {
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                return userDao.add(user);
            } else {
                return false;
            }
        }

        throw new ServiceException(INVALID_USER_MESSAGE);
    }

    @Override
    public boolean removeUserById(String id) {
        return userDao.removeById(Long.parseLong(id));
    }

    @Override
    public boolean updateUser(User user) {
        setStandardPicture(user);
        if (userValidator.validate(user) && user.getId() != 0) {
            if (userUpdatingDuplicationChecker.check(user)) {
                return userDao.update(user);
            } else {
                return false;
            }
        }

        throw new ServiceException(INVALID_USER_MESSAGE);
    }

    @Override
    public Optional<Basket> findBasketByUserId(String userId) {
        return userDao.findBasketByUserId(Long.parseLong(userId));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public boolean removeBasketByUserId(String userId) {
        return userDao.clearBasketByUserId(Long.parseLong(userId));
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
                goodAddingResult = userDao.changeGoodQuantity(longUserId, longGoodId, (short) (basketOptional.get()
                        .getGoods().entrySet().stream()
                        .filter(entry -> entry.getKey().getId() == longGoodId)
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .orElseThrow(() -> new ServiceException(INVALID_GOOD_ID_MESSAGE)) + 1));
            }

            if (goodAddingResult) {
                transactionManager.commit(transaction);
            } else {
                transactionManager.rollback(transaction);
            }

            return goodAddingResult;
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
            short shortQuantity = Short.parseShort(quantity);

            if (shortQuantity < 1 || shortQuantity > 999) {
                throw new ServiceException(String.format(INVALID_GOOD_QUANTITY, shortQuantity));
            }

            Optional<Basket> basketOptional = userDao.findBasketByUserId(longUserId);
            if (!basketOptional.isPresent() || basketOptional.get().getGoods().entrySet().stream()
                    .noneMatch(entry -> entry.getKey().getId() == longGoodId)) {
                throw new ServiceException(GOOD_THERE_IS_NOT_MESSAGE);
            }

            boolean goodUpdatingResult = userDao.changeGoodQuantity(longUserId, longGoodId, shortQuantity);

            if (goodUpdatingResult) {
                transactionManager.commit(transaction);
            } else {
                transactionManager.rollback(transaction);
            }

            return goodUpdatingResult;
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    private void setStandardPicture(User user) {
        if (user != null && user.getProfilePictureName().equals(EMPTY_PICTURE_NAME)) {
            user.setProfilePictureName(STANDARD_USER_PICTURE_NAME);
        }
    }
}
