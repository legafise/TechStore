package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionManager;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;
import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.entity.OrderStatus;
import by.lashkevich.logic.service.*;
import by.lashkevich.logic.service.validator.GoodValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JWDGoodService implements GoodService {
    private static final String EMPTY_PICTURE_NAME = "";
    private static final String NONEXISTENT_GOOD_ID_MESSAGE = "Nonexistent good id was received";
    private static final String INVALID_GOOD_MESSAGE = "Invalid good was received";
    private static final String STANDARD_GOOD_PICTURE = "default.jpg";
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final Predicate<Good> goodValidator;
    private final GoodDao goodDao;
    private final TransactionManager transactionManager;

    public JWDGoodService() {
        transactionManager = TransactionManager.getInstance();
        goodValidator = new GoodValidator();
        goodDao = (GoodDao) DaoFactory.GOOD_DAO.getDao();
        reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    @Override
    public List<Good> findAllGoods() throws ServiceException {
        try {
            return goodDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<GoodType> findAllGoodTypes() throws ServiceException {
        try {
            return goodDao.findAllTypes();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Good findGoodById(String id) throws ServiceException {
        try {
            Optional<Good> goodOptional = goodDao.findById(Long.parseLong(id));

            if (goodOptional.isPresent()) {
                return goodOptional.get();
            }

            throw new ServiceException(NONEXISTENT_GOOD_ID_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public GoodType findTypeById(String typeId) {
        try {
            return goodDao.findTypeById(Integer.parseInt(typeId));
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean isBoughtGood(String goodId, String userId) {
        try {
            return orderService.findOrdersByUserId(userId).map(orderList -> orderList.stream()
                    .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                    .anyMatch(order -> order.getGoods().keySet().stream()
                            .anyMatch(good -> good.getId() == Integer.parseInt(goodId)))).orElse(false);
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addGood(Good good) throws ServiceException {
        try {
            if (goodValidator.test(good)) {
                setStandardPicture(good);
                return goodDao.add(good);
            }

            throw new ServiceException(INVALID_GOOD_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean removeGoodById(String id) throws ServiceException {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Good good = findGoodById(id);
            if (good.getReviews().stream()
                    .allMatch(review -> reviewService.removeReviewById(String.valueOf(review.getId())))
                    && goodDao.removeById(Long.parseLong(id))) {
                transactionManager.commit(transaction);
                return true;
            }

            transactionManager.rollback(transaction);
            return false;
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean updateGood(Good good) throws ServiceException {
        try {
            if (goodValidator.test(good) && good.getId() != 0) {
                setStandardPicture(good);
                return goodDao.update(good);
            }

            throw new ServiceException(INVALID_GOOD_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void setStandardPicture(Good good) {
        if (good.getImgName().equals(EMPTY_PICTURE_NAME)) {
            good.setImgName(STANDARD_GOOD_PICTURE);
        }
    }
}