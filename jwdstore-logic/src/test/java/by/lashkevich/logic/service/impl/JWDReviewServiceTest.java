package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.ReviewDao;
import by.lashkevich.logic.entity.Review;
import by.lashkevich.logic.entity.Role;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWDReviewServiceTest {
    private JWDReviewService reviewService;
    private ReviewDao reviewDao;
    private Review firstTestReview;
    private Review secondTestReview;

    @BeforeEach
    void setUp() {
        reviewService = new JWDReviewService();
        reviewDao = mock(ReviewDao.class);
        reviewService.setReviewDao(reviewDao);
        User author = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);
        firstTestReview = new Review(1, (short) 5, "Good phone", author);
        secondTestReview = new Review(2, (short) 4, "Good thing but it could be better", author);
    }

    @Test
    void findAllReviewsTest() {
        List<Review> reviewList = Arrays.asList(firstTestReview, secondTestReview);
        when(reviewDao.findAll()).thenReturn(reviewList);
        Assert.assertEquals(reviewList, reviewService.findAllReviews());
    }

    @Test
    void findReviewByIdTest() {
        when(reviewDao.findById(1L)).thenReturn(Optional.of(firstTestReview));
        Assert.assertEquals(firstTestReview, reviewService.findReviewById("1"));
    }

    @Test
    void findReviewWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> reviewService.findReviewById("O"));
    }

    @Test
    void updateReviewTest() {
        when(reviewDao.update(firstTestReview)).thenReturn(true);
        Assert.assertTrue(reviewService.updateReview(firstTestReview));
    }

    @Test
    void updateReviewWithInvalidReviewIdTest() {
        firstTestReview.setId(0);
        Assert.assertThrows(ServiceException.class, () -> reviewService.updateReview(firstTestReview));
    }

    @Test
    void isCreatedReviewTest() {
        when(reviewDao.findReviewByUserAndGoodId(1L, 1L)).thenReturn(Optional.of(firstTestReview));
        Assert.assertFalse(reviewService.isCreatedReview("1", "1"));
    }

    @Test
    void isCreatedReviewTestWithInvalidUserAndGoodId() {
        Assert.assertThrows(ServiceException.class, () -> reviewService.isCreatedReview("wr", "erw"));
    }
}