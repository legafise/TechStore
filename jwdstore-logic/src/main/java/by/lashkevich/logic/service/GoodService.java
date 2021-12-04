package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;

import java.util.List;

public interface GoodService extends Service {
    List<Good> findAllGoods() throws ServiceException;

    List<GoodType> findAllGoodTypes() throws ServiceException;

    Good findGoodById(String id) throws ServiceException;

    boolean addGood(Good good) throws ServiceException;

    boolean removeGoodById(String id) throws ServiceException;

    boolean updateGood(Good good) throws ServiceException;

    GoodType findTypeById(String typeId);
}
