package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;
import by.lashkevich.logic.service.checker.FileFormatChecker;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoodValidatorTest {
    private GoodValidator goodValidator;
    private Good testGood;
    private FileFormatChecker formatChecker;

    @BeforeEach
    void setUp() {
        goodValidator = new GoodValidator();
        formatChecker = mock(FileFormatChecker.class);
        goodValidator.setFormatChecker(formatChecker);

        GoodType testType = new GoodType((short) 1, "Phone");
        testGood = new Good(1, "Iphone", new BigDecimal("1500"), "Created by Apple",
                testType, "default.jpg");
    }

    @Test
    void validateRightGoodTest() {
        when(formatChecker.checkImgFormat("default.jpg")).thenReturn(true);
        Assert.assertTrue(goodValidator.validate(testGood));
    }

    @Test
    void validateNullGoodTest() {
        Assert.assertFalse(goodValidator.validate(null));
    }

    @Test
    void validateGoodWithInvalidNameTest() {
        testGood.setName("d");
        Assert.assertFalse(goodValidator.validate(testGood));
    }

    @Test
    void validateGoodWithInvalidDescriptionTest() {
        testGood.setDescription("Apple");
        Assert.assertFalse(goodValidator.validate(testGood));
    }

    @Test
    void validateGoodWithInvalidPriceTest() {
        testGood.setPrice(new BigDecimal("-21"));
        Assert.assertFalse(goodValidator.validate(testGood));
    }

    @Test
    void validateGoodWithNullTypeTest() {
        testGood.setType(null);
        Assert.assertFalse(goodValidator.validate(testGood));
    }

    @Test
    void validateGoodWithNullImgNameTest() {
        testGood.setImgName(null);
        Assert.assertFalse(goodValidator.validate(testGood));
    }

    @Test
    void validateGoodWithInvalidImgNameTest() {
        testGood.setImgName("video.avi");
        when(formatChecker.checkImgFormat("video.avi")).thenReturn(false);
        Assert.assertFalse(goodValidator.validate(testGood));
    }
}