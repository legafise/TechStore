package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionManager;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;
import by.lashkevich.logic.entity.OrderStatus;
import by.lashkevich.logic.service.*;
import by.lashkevich.logic.service.validator.GoodValidator;

import java.util.List;
import java.util.Optional;

/**
 * The type Jwd good service.
 *
 * @author Roman Lashkevich
 * @see GoodService
 */
public class JWDGoodService implements GoodService {
    private static final String EMPTY_PICTURE_NAME = "";
    private static final String NONEXISTENT_GOOD_ID_MESSAGE = "Nonexistent good";
    private static final String INVALID_GOOD_MESSAGE = "Invalid good data";
    private static final String INVALID_GOOD_TYPE_MESSAGE = "Invalid good type";
    private static final String STANDARD_GOOD_PICTURE = "default.jpg";
    private final ReviewService reviewService;
    private final TransactionManager transactionManager;
    private OrderService orderService;
    private GoodValidator goodValidator;
    private GoodDao goodDao;

    public JWDGoodService() {
        transactionManager = TransactionManager.getInstance();
        goodValidator = new GoodValidator();
        goodDao = (GoodDao) DaoFactory.GOOD_DAO.getDao();
        reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
        orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
    }

    public void setGoodDao(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    public void setGoodValidator(GoodValidator goodValidator) {
        this.goodValidator = goodValidator;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<Good> findAllGoods() {
        return goodDao.findAll();
    }

    @Override
    public List<GoodType> findAllGoodTypes() {
        return goodDao.findAllTypes();
    }

    @Override
    public Good findGoodById(String id) {
        Optional<Good> goodOptional = goodDao.findById(Long.parseLong(id));

        if (goodOptional.isPresent()) {
            return goodOptional.get();
        }

        throw new ServiceException(NONEXISTENT_GOOD_ID_MESSAGE);
    }

    @Override
    public GoodType findTypeById(String typeId) {
        Optional<GoodType> goodType = goodDao.findTypeById(Integer.parseInt(typeId));
        if (goodType.isPresent()) {
            return goodType.get();
        }

        throw new ServiceException(INVALID_GOOD_TYPE_MESSAGE);
    }

    @Override
    public boolean isBoughtGood(String goodId, String userId) {
        return orderService.findOrdersByUserId(userId).map(orderList -> orderList.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .anyMatch(order -> order.getGoods().keySet().stream()
                        .anyMatch(good -> good.getId() == Integer.parseInt(goodId))))
                .orElse(false);
    }

    @Override
    public boolean addGood(Good good) {
        setStandardPicture(good);
        if (goodValidator.validate(good)) {
            return goodDao.add(good);
        }

        throw new ServiceException(INVALID_GOOD_MESSAGE);
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
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean updateGood(Good good) {
        if (goodValidator.validate(good) && good.getId() != 0) {
            setStandardPicture(good);
            return goodDao.update(good);
        }

        throw new ServiceException(INVALID_GOOD_MESSAGE);
    }

    private void setStandardPicture(Good good) {
        if (good != null && good.getImgName().equals(EMPTY_PICTURE_NAME)) {
            good.setImgName(STANDARD_GOOD_PICTURE);
        }
    }
}