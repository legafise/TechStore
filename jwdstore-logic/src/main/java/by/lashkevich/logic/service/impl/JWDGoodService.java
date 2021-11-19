package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.GoodValidator;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JWDGoodService implements GoodService {
    private static final String NONEXISTENT_GOOD_ID_MESSAGE = "Nonexistent good id was received";
    private static final String INVALID_GOOD_MESSAGE = "Invalid good was received";
    private static final String STANDARD_GOOD_PICTURE = "goods_default.jpg";
    private final Predicate<Good> goodValidator;
    private final GoodDao goodDao;

    public JWDGoodService() {
        goodValidator = new GoodValidator();
        goodDao = (GoodDao) DaoFactory.GOOD_DAO.getDao();
    }

    @Override
    public List<Good> findAll() throws ServiceException {
        try {
            return goodDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Good findById(String id) throws ServiceException {
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
    public boolean add(Good good) throws ServiceException {
        try {
            if (goodValidator.test(good)) {
                Transaction transaction = TransactionFactory.getInstance().createTransaction();
                setStandardPicture(good);
                boolean isGoodAdded = goodDao.add(good);

                if (isGoodAdded) {
                    transaction.commit();
                }

                transaction.closeTransaction();
                return isGoodAdded;
            }

            throw new ServiceException(INVALID_GOOD_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean removeById(String id) throws ServiceException {
        try {
            Transaction transaction = TransactionFactory.getInstance().createTransaction();
            boolean isGoodRemoved = goodDao.removeById(Long.parseLong(id));
            if (isGoodRemoved) {
                transaction.commit();
            }

            return isGoodRemoved;
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(Good good) throws ServiceException {
        try {
            Transaction transaction = TransactionFactory.getInstance().createTransaction();
            if (goodValidator.test(good) && good.getId() != 0) {
                setStandardPicture(good);
                boolean isGoodUpdated = goodDao.update(good);

                if (isGoodUpdated) {
                    transaction.commit();
                }

                transaction.closeTransaction();
                return isGoodUpdated;
            }

            throw new ServiceException(INVALID_GOOD_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void setStandardPicture(Good good) {
        if (good.getImgURL() == null) {
            good.setImgURL(STANDARD_GOOD_PICTURE);
        }
    }
}