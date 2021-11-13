package by.lashkevich.logic.dao;

import by.lashkevich.logic.dao.impl.JWDGoodDao;
import by.lashkevich.logic.dao.impl.JWDOrderDao;
import by.lashkevich.logic.dao.impl.JWDReviewDao;
import by.lashkevich.logic.dao.impl.JWDUserDao;

public enum DaoType {
    GOOD_DAO() {
        @Override
        public GoodDao createDao() {
            return new JWDGoodDao();
        }
    },
    ORDER_DAO {
        @Override
        public OrderDao createDao() {
            return new JWDOrderDao();
        }
    },
    REVIEW_DAO {
        @Override
        public ReviewDao createDao() {
            return new JWDReviewDao();
        }
    },
    USER_DAO {
        @Override
        public UserDao createDao() {
            return new JWDUserDao();
        }
    };

    public abstract BaseDao<?, ?> createDao();
}
