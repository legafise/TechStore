package by.lashkevich.logic.dao;

import by.lashkevich.logic.dao.impl.JWDGoodDao;

public enum DaoFactory {
    GOOD_DAO(new JWDGoodDao());

    private final BaseDao<?, ?> dao;

    DaoFactory(BaseDao<?, ?> dao) {
        this.dao = dao;
    }

    public BaseDao<?, ?> getDao() {
        return dao;
    }
}
