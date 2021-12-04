package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;

import java.util.List;

public interface GoodDao extends BaseDao<Long, Good> {
    List<GoodType> findAllTypes();
    GoodType findTypeById(int typeId);
}
