package by.lashkevich.logic.service;

import by.lashkevich.logic.entity.Review;

import java.util.List;

/**
 * The interface Review service.
 * @author Roman Lashkevich
 */
public interface ReviewService extends Service {
    /**
     * Find all reviews list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Review> findAllReviews() throws ServiceException;

    /**
     * Find review by id review.
     *
     * @param id the id
     * @return the review
     * @throws ServiceException the service exception
     */
    Review findReviewById(String id) throws ServiceException;

    /**
     * Add review boolean.
     *
     * @param review the review
     * @param goodId the good id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addReview(Review review, String goodId) throws ServiceException;

    /**
     * Remove review by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeReviewById(String id) throws ServiceException;

    /**
     * Update review boolean.
     *
     * @param review the review
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateReview(Review review) throws ServiceException;

    /**
     * Check review for duplication boolean.
     *
     * @param userId the user id
     * @param goodId the good id
     * @return the boolean
     */
    boolean checkReviewForDuplication(String userId, String goodId);
}
