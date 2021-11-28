package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Review;

import java.util.List;

public interface ReviewService extends Service {
    List<Review> findAllReviews() throws ServiceException;

    Review findReviewById(String id) throws ServiceException;

    boolean addReview(Review review, String goodId) throws ServiceException;

    boolean removeReviewById(String id) throws ServiceException;

    boolean updateReview(Review review) throws ServiceException;

    boolean checkReviewForDuplication(String userId, String goodId);
}
