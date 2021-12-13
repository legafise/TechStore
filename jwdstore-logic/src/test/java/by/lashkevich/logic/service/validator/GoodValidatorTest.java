package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GoodValidatorTest {
    private GoodValidator goodValidator;
    private Good testGood;

    @BeforeEach
    void setUp() {
        goodValidator = new GoodValidator();

        GoodType testType = new GoodType(1, "Phone");
        testGood = new Good(1, "Iphone", new BigDecimal("1500"), "Created by Apple",
                testType, "default.jpg");
    }

    @Test
    void validateRightGoodTest() {
        Assert.assertTrue(goodValidator.test(testGood));
    }

    @Test
    void validateNullGoodTest() {
        Assert.assertFalse(goodValidator.test(null));
    }

    @Test
    void validateGoodWithInvalidNameTest() {
        testGood.setName("d");
        Assert.assertFalse(goodValidator.test(testGood));
    }

    @Test
    void validateGoodWithInvalidDescriptionTest() {
        testGood.setDescription("Apple");
        Assert.assertFalse(goodValidator.test(testGood));
    }

    @Test
    void validateGoodWithInvalidPriceTest() {
        testGood.setPrice(new BigDecimal("-21"));
        Assert.assertFalse(goodValidator.test(testGood));
    }

    @Test
    void validateGoodWithNullTypeTest() {
        testGood.setType(null);
        Assert.assertFalse(goodValidator.test(testGood));
    }
}