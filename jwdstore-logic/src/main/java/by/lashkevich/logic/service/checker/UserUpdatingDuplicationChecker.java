package by.lashkevich.logic.service.checker;

import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.User;

import java.util.Optional;
import java.util.function.Predicate;

public class UserUpdatingDuplicationChecker implements Predicate<User> {
    private final UserDao userDao;

    public UserUpdatingDuplicationChecker() {
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
    }

    @Override
    public boolean test(User user) {
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
