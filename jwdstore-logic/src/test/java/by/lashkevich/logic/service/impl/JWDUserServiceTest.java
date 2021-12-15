package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.entity.*;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.UserService;
import by.lashkevich.logic.service.checker.UserAddingDuplicationChecker;
import by.lashkevich.logic.service.checker.UserUpdatingDuplicationChecker;
import by.lashkevich.logic.service.validator.UserValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWDUserServiceTest {
    private JWDUserService userService;
    private UserDao userDao;
    private User firstTestUser;
    private User secondTestUser;
    private UserAddingDuplicationChecker addingDuplicationChecker;
    private UserUpdatingDuplicationChecker userUpdatingDuplicationChecker;
    private UserValidator userValidator;
    private Basket testBasket;

    @BeforeEach
    void setUp() {
        userService = new JWDUserService();
        userDao = mock(UserDao.class);
        userValidator = mock(UserValidator.class);
        addingDuplicationChecker = mock(UserAddingDuplicationChecker.class);
        userUpdatingDuplicationChecker = mock(UserUpdatingDuplicationChecker.class);

        userService.setUserAddingDuplicationChecker(addingDuplicationChecker);
        userService.setUserDao(userDao);
        userService.setUserValidator(userValidator);
        userService.setUserUpdatingDuplicationChecker(userUpdatingDuplicationChecker);

        firstTestUser = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);
        secondTestUser = new User(2, "Gleb", "Kirienko", "SirPakun", "ZimFloppa",
                "pakun@gmail.com", LocalDate.now(), "default.jpg", new BigDecimal("150.34"), Role.USER);

        GoodType firstTestType = new GoodType((short) 1, "Phone");
        GoodType secondTestType = new GoodType((short) 2, "Console");

        Good firstTestGood = new Good(1, "Iphone", new BigDecimal("1500"), "Iphone",
                firstTestType, "default.jpg");
        Good secondTestGood = new Good(2, "Play Station 5", new BigDecimal("2000"),
                "Play Station 5", secondTestType, "default.jpg");

        Map<Good, Short> goods = new HashMap<>();
        goods.put(firstTestGood, (short) 1);
        goods.put(secondTestGood, (short) 1);

        testBasket = new Basket(goods, secondTestUser);
    }

    @Test
    void findAllUsersTest() {
        List<User> userList = Arrays.asList(firstTestUser, secondTestUser);
        when(userDao.findAll()).thenReturn(userList);
        Assert.assertEquals(userList, userService.findAllUsers());
    }

    @Test
    void findUserByIdTest() {
        when(userDao.findById(1L)).thenReturn(Optional.of(firstTestUser));
        Assert.assertEquals(firstTestUser, userService.findUserById("1"));
    }

    @Test
    void findUserWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> userService.findUserById("egeg"));
    }

    @Test
    void addUserTest() {
        when(userDao.add(firstTestUser)).thenReturn(true);
        when(userValidator.validate(firstTestUser)).thenReturn(true);
        when(addingDuplicationChecker.check(firstTestUser)).thenReturn(true);
        Assert.assertTrue(userService.addUser(firstTestUser));
    }

    @Test
    void removeUserByIdTest() {
        when(userDao.removeById(1L)).thenReturn(true);
        Assert.assertTrue(userService.removeUserById("1"));
    }

    @Test
    void removeUserWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> userService.removeUserById("dada"));
    }

    @Test
    void updateUserTest() {
        when(userValidator.validate(firstTestUser)).thenReturn(true);
        when(userUpdatingDuplicationChecker.check(firstTestUser)).thenReturn(true);
        when(userDao.update(firstTestUser)).thenReturn(true);
        Assert.assertTrue(userService.updateUser(firstTestUser));
    }

    @Test
    void updateUserWithInvalidIdTest() {
        firstTestUser.setId(0);
        when(userValidator.validate(firstTestUser)).thenReturn(true);
        when(userUpdatingDuplicationChecker.check(firstTestUser)).thenReturn(true);
        when(userDao.update(firstTestUser)).thenReturn(true);
        Assert.assertThrows(ServiceException.class, () -> userService.updateUser(firstTestUser));
    }

    @Test
    void findBasketByUserIdTest() {
        when(userDao.findBasketByUserId(2L)).thenReturn(Optional.of(testBasket));
        Assert.assertEquals(Optional.of(testBasket), userService.findBasketByUserId("2"));
    }

    @Test
    void findUserByEmailTest() {
        when(userDao.findByEmail("lash@ya.ru")).thenReturn(Optional.of(firstTestUser));
        Assert.assertEquals(Optional.of(firstTestUser), userService.findUserByEmail("lash@ya.ru"));
    }

    @Test
    void findUserByLoginTest() {
        when(userDao.findByLogin("legafise")).thenReturn(Optional.of(firstTestUser));
        Assert.assertEquals(Optional.of(firstTestUser), userService.findUserByLogin("legafise"));
    }

    @Test
    void removeBasketByUserIdTest() {
        when(userDao.clearBasketByUserId(1L)).thenReturn(true);
        Assert.assertTrue(userService.removeBasketByUserId("1"));
    }

    @Test
    void removeBasketWithInvalidUserIdTest() {
        Assert.assertThrows(ServiceException.class, () -> userService.removeBasketByUserId("ege"));
    }
}