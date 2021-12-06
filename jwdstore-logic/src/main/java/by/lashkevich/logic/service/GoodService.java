package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;

import java.util.List;

/**
 * The interface Good service.
 * @author Roman Lashkevich
 */
public interface GoodService extends Service {
    /**
     * Find all goods list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Good> findAllGoods() throws ServiceException;

    /**
     * Find all good types list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<GoodType> findAllGoodTypes() throws ServiceException;

    /**
     * Find good by id good.
     *
     * @param id the id
     * @return the good
     * @throws ServiceException the service exception
     */
    Good findGoodById(String id) throws ServiceException;

    /**
     * Add good boolean.
     *
     * @param good the good
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addGood(Good good) throws ServiceException;

    /**
     * Remove good by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeGoodById(String id) throws ServiceException;

    /**
     * Update good boolean.
     *
     * @param good the good
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateGood(Good good) throws ServiceException;

    /**
     * Find type by id good type.
     *
     * @param typeId the type id
     * @return the good type
     */
    GoodType findTypeById(String typeId);

    /**
     * Is bought good boolean.
     *
     * @param goodId the good id
     * @param userId the user id
     * @return the boolean
     */
    boolean isBoughtGood(String goodId, String userId);
}
