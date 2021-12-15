package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;

import java.util.List;
import java.util.Optional;

/**
 * The interface Good dao.
 * @author Roman Lashkevich
 * @see BaseDao
 */
public interface GoodDao extends BaseDao<Long, Good> {
    /**
     * Find all types list.
     *
     * @return the list
     */
    List<GoodType> findAllTypes();

    /**
     * Find type by id good type.
     *
     * @param typeId the type id
     * @return the good type
     */
    Optional<GoodType> findTypeById(int typeId);
}
