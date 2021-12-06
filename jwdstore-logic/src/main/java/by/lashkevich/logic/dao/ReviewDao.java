package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Review;

import java.util.List;
import java.util.Optional;

/**
 * The interface Review dao.
 * @author Roman Lashkevich
 * @see BaseDao
 */
public interface ReviewDao extends BaseDao<Long, Review> {
    boolean connectReviewToGood(Long reviewId, Long goodId);
    List<Review> findByUserId(Long userId);
    boolean removeConnectionBetweenReviewAndGood(Long reviewId);
    Optional<Review> findReviewByUserAndGoodId(Long userId, Long goodId);
}
