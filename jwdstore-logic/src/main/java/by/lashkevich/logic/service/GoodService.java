package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Good;

import java.util.List;

public interface GoodService extends Service {
    List<Good> findAllGoods() throws ServiceException;

    Good findGoodById(String id) throws ServiceException;

    boolean addGood(Good good) throws ServiceException;

    boolean removeGoodById(String id) throws ServiceException;

    boolean updateGood(Good good) throws ServiceException;
}
