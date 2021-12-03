package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Good;

import java.util.List;

public interface GoodDao extends BaseDao<Long, Good> {
    List<String> findAllTypes();
}
