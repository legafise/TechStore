package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.checker.FileFormatChecker;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserValidatorTest {
    private UserValidator userValidator;
    private User testUser;
    private FileFormatChecker formatChecker;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
        formatChecker = mock(FileFormatChecker.class);
        userValidator.setFormatChecker(formatChecker);
        testUser = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.of(2000, 10, 15), "default.jpg", new BigDecimal("50.34"), Role.USER);
    }

    @Test
    void validateRightUserTest() {
        when(formatChecker.checkImgFormat("default.jpg")).thenReturn(true);
        Assert.assertTrue(userValidator.validate(testUser));
    }

    @Test
    void validateNullUserTest() {
        Assert.assertFalse(userValidator.validate(null));
    }

    @Test
    void validateUserWithInvalidNameTest() {
        testUser.setName("F");
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithInvalidSurnameTest() {
        testUser.setSurname("d");
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithInvalidPasswordTest() {
        testUser.setPassword("123");
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithInvalidLoginTest() {
        testUser.setLogin("s");
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithInvalidEmailTest() {
        testUser.setEmail("lash.gmaail@com");
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithNullRoleTest() {
        testUser.setRole(null);
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithNullImgNameTest() {
        testUser.setProfilePictureName(null);
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithInvalidBirthDateTest() {
        testUser.setBirthDate(LocalDate.of(1000, 10,10));
        Assert.assertFalse(userValidator.validate(testUser));
    }

    @Test
    void validateUserWithInvalidPictureDateTest() {
        testUser.setProfilePictureName("video.mp4");
        when(formatChecker.checkImgFormat("video.mp4")).thenReturn(false);
        Assert.assertFalse(userValidator.validate(testUser));
    }
}