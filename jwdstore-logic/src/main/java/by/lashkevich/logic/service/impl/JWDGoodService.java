package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.GoodValidator;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JWDGoodService implements GoodService {
    private static final String EMPTY_PICTURE_NAME = "";
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
    public List<Good> findAllGoods() throws ServiceException {
        try {
            return goodDao.findAll();
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
        try {
            return goodDao.removeById(Long.parseLong(id));
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
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
        if (good.getImgURL().equals(EMPTY_PICTURE_NAME)) {
            good.setImgURL(STANDARD_GOOD_PICTURE);
        }
    }
}