package by.lashkevich.logic.service.checker;

import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.User;

/**
 * The type User adding duplication checker.
 * @author Roman Lashkevich
 */
public class UserAddingDuplicationChecker {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Instantiates a new User adding duplication checker.
     */
    public UserAddingDuplicationChecker() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
    }

    public boolean check(User user) {
        return !userDao.findByEmail(user.getEmail()).isPresent()
                && !userDao.findByLogin(user.getLogin()).isPresent();
    }
}
