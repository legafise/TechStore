package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoType;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.service.GoodService;
import by.lashkevich.logic.service.ServiceException;

import java.util.List;

public class JWDGoodService implements GoodService {
    private Transaction transaction;
    private TransactionFactory transactionFactory;

    @Override
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void setTransactionFactory(TransactionFactory factory) {
        transactionFactory = factory;
    }

    @Override
    public void closeTransaction() throws ServiceException {
        try {
            transactionFactory.closeTransaction();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Good> findAll() throws ServiceException {
        try {
            GoodDao goodDao = transaction.createDao(DaoType.GOOD_DAO);
            return goodDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Good findById(String id) {
        return null;
    }

    @Override
    public boolean add(Good good) throws ServiceException {
        try {
            GoodDao goodDao = transaction.createDao(DaoType.GOOD_DAO);
            boolean isGoodAdded = goodDao.add(good);
            if (isGoodAdded) {
                transaction.commit();
            } else {
                transaction.rollback();
            }

            return isGoodAdded;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

    @Override
    public boolean update(Good entity) {
        return false;
    }
}