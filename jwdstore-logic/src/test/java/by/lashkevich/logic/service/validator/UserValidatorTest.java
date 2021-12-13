package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

class UserValidatorTest {
    private UserValidator userValidator;
    private User testUser;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
        testUser = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.of(2000, 10, 15), "default.jpg", new BigDecimal("50.34"), Role.USER);
    }

    @Test
    void validateRightUserTest() {
        Assert.assertTrue(userValidator.test(testUser));
    }

    @Test
    void validateNullUserTest() {
        Assert.assertFalse(userValidator.test(null));
    }

    @Test
    void validateUserWithInvalidNameTest() {
        testUser.setName("F");
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithInvalidSurnameTest() {
        testUser.setSurname("d");
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithInvalidPasswordTest() {
        testUser.setPassword("123");
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithInvalidLoginTest() {
        testUser.setLogin("s");
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithInvalidEmailTest() {
        testUser.setEmail("lash.gmaail@com");
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithNullRoleTest() {
        testUser.setRole(null);
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithNullImgNameTest() {
        testUser.setProfilePictureName(null);
        Assert.assertFalse(userValidator.test(testUser));
    }

    @Test
    void validateUserWithInvalidBirthDateTest() {
        testUser.setBirthDate(LocalDate.of(1000, 10,10));
        Assert.assertFalse(userValidator.test(testUser));
    }
}