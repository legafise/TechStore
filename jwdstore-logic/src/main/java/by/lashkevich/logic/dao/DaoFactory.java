package by.lashkevich.logic.dao;

import by.lashkevich.logic.dao.impl.JWDGoodDao;
import by.lashkevich.logic.dao.impl.JWDOrderDao;
import by.lashkevich.logic.dao.impl.JWDReviewDao;
import by.lashkevich.logic.dao.impl.JWDUserDao;

/**
 * The enum Dao factory.
 * @author Roman Lashkevich
 */
public enum DaoFactory {
    /**
     * The Good dao.
     */
    GOOD_DAO(new JWDGoodDao()),
    /**
     * The User dao.
     */
    USER_DAO(new JWDUserDao()),
    /**
     * The Order dao.
     */
    ORDER_DAO(new JWDOrderDao()),
    /**
     * The Review dao.
     */
    REVIEW_DAO(new JWDReviewDao());

    private final BaseDao<?, ?> dao;

    DaoFactory(BaseDao<?, ?> dao) {
        this.dao = dao;
    }

    /**
     * Gets dao.
     *
     * @return the dao
     */
    public BaseDao<?, ?> getDao() {
        return dao;
    }
}
