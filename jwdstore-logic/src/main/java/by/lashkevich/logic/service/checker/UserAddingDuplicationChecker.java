package by.lashkevich.logic.service.checker;

import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.User;

import java.util.function.Predicate;

public class UserAddingDuplicationChecker implements Predicate<User> {
    private final UserDao userDao;

    public UserAddingDuplicationChecker() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
    }

    @Override
    public boolean test(User user) {
        return !userDao.findByEmail(user.getEmail()).isPresent()
                && !userDao.findByLogin(user.getLogin()).isPresent();
    }
}
