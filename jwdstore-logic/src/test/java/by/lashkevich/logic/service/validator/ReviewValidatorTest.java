package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Review;
import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReviewValidatorTest {
    private ReviewValidator reviewValidator;
    private Review testReview;

    @BeforeEach
    void setUp() {
        reviewValidator = new ReviewValidator();
        User author = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);
        testReview = new Review(1, (short) 5, "Cool and not expensive phone!", author);
    }

    @Test
    void validateRightReviewTest() {
        Assert.assertTrue(reviewValidator.test(testReview));
    }

    @Test
    void validateNullReviewTest() {
        Assert.assertFalse(reviewValidator.test(null));
    }

    @Test
    void validateReviewWithNullAuthorTest() {
        testReview.setAuthor(null);
        Assert.assertFalse(reviewValidator.test(testReview));
    }

    @Test
    void validateReviewWithInvalidRateTest() {
        testReview.setRate((short) -1);
        Assert.assertFalse(reviewValidator.test(testReview));
    }

    @Test
    void validateReviewWithInvalidContentTest() {
        testReview.setContent("x");
        Assert.assertFalse(reviewValidator.test(testReview));
    }
}