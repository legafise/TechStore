package by.lashkevich.logic.service.checker;

import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.User;

import java.util.Optional;

/**
 * The type User updating duplication checker.
 */
public class UserUpdatingDuplicationChecker {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Instantiates a new User updating duplication checker.
     */
    public UserUpdatingDuplicationChecker() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
    }

    public boolean check(User user) {
        return checkEmailDuplication(user) && checkLoginDuplication(user);
    }

    private boolean checkEmailDuplication(User user) {
        Optional<User> optionalUser = userDao.findByEmail(user.getEmail());
        return !optionalUser.isPresent() || optionalUser.get().getId() == user.getId();
    }

    private boolean checkLoginDuplication(User user) {
        Optional<User> optionalUser = userDao.findByLogin(user.getLogin());
        return !optionalUser.isPresent() || optionalUser.get().getId() == user.getId();
    }
}
