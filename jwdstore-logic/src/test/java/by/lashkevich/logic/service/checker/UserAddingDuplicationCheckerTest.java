package by.lashkevich.logic.service.checker;

import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserAddingDuplicationCheckerTest {
    private UserAddingDuplicationChecker duplicationChecker;
    private UserDao userDao;
    private User firstTestUser;
    private User secondTestUser;

    @BeforeEach
    void setUp() {
        duplicationChecker = new UserAddingDuplicationChecker();
        userDao = mock(UserDao.class);
        duplicationChecker.setUserDao(userDao);

        firstTestUser = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);
        secondTestUser = new User(2, "Gleb", "Kirienko", "SirPakun", "ZimFloppa",
                "pakun@gmail.com", LocalDate.now(), "default.jpg", new BigDecimal("150.34"), Role.USER);
    }

    @Test
    void checkUserForEmailDuplicationWithUniqueDataTest() {
        when(userDao.findByEmail("lash@ya.ru")).thenReturn(Optional.empty());
        when(userDao.findByLogin("legafise")).thenReturn(Optional.empty());
        Assert.assertTrue(duplicationChecker.check(firstTestUser));
    }

    @Test
    void checkUserForEmailDuplicationWithDuplicateEmailTest() {
        when(userDao.findByEmail("pakun@gmail.com")).thenReturn(Optional.of(secondTestUser));
        when(userDao.findByLogin("legafise")).thenReturn(Optional.empty());
        firstTestUser.setEmail("pakun@gmail.com");
        Assert.assertFalse(duplicationChecker.check(firstTestUser));
    }

    @Test
    void checkUserForEmailDuplicationWithDuplicateLoginTest() {
        when(userDao.findByEmail("lash@ya.ru")).thenReturn(Optional.empty());
        when(userDao.findByLogin("SirPakun")).thenReturn(Optional.of(secondTestUser));
        firstTestUser.setLogin("SirPakun");
        Assert.assertFalse(duplicationChecker.check(firstTestUser));
    }
}