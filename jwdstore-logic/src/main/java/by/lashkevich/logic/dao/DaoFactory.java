package by.lashkevich.logic.dao;

import by.lashkevich.logic.dao.impl.JWDGoodDao;
import by.lashkevich.logic.dao.impl.JWDOrderDao;
import by.lashkevich.logic.dao.impl.JWDReviewDao;
import by.lashkevich.logic.dao.impl.JWDUserDao;

public enum DaoFactory {
    GOOD_DAO(new JWDGoodDao()),
    USER_DAO(new JWDUserDao()),
    ORDER_DAO(new JWDOrderDao()),
    REVIEW_DAO(new JWDReviewDao());

    private final BaseDao<?, ?> dao;

    DaoFactory(BaseDao<?, ?> dao) {
        this.dao = dao;
    }

    public BaseDao<?, ?> getDao() {
        return dao;
    }
}
