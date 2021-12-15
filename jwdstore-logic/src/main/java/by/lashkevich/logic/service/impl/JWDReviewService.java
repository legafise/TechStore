package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.ReviewDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionManager;
import by.lashkevich.logic.entity.Review;
import by.lashkevich.logic.service.ReviewService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.ReviewValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Jwd review service.
 *
 * @author Roman Lashkevich
 * @see ReviewService
 */
public class JWDReviewService implements ReviewService {
    private static final String NONEXISTENT_REVIEW_ID_MESSAGE = "Nonexistent review id was received";
    private static final String INVALID_REVIEW_MESSAGE = "Invalid review";
    private final ReviewValidator reviewValidator;
    private final TransactionManager transactionManager;
    private ReviewDao reviewDao;

    public JWDReviewService() {
        reviewDao = (ReviewDao) DaoFactory.REVIEW_DAO.getDao();
        reviewValidator = new ReviewValidator();
        transactionManager = TransactionManager.getInstance();
    }

    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewDao.findAll();
    }

    @Override
    public Review findReviewById(String id) {
        Optional<Review> reviewOptional = reviewDao.findById(Long.parseLong(id));

        if (reviewOptional.isPresent()) {
            return reviewOptional.get();
        }

        throw new ServiceException(NONEXISTENT_REVIEW_ID_MESSAGE);
    }

    @Override
    public boolean addReview(Review review, String goodId) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            if (isCreatedReview(String.valueOf(review.getAuthor().getId()), goodId)
                    && reviewValidator.validate(review) && reviewDao.add(review)) {
                Long reviewId = findCurrentReviewId(review);
                if (reviewDao.connectReviewToGood(reviewId, Long.parseLong(goodId))) {
                    transactionManager.commit(transaction);
                    return true;
                } else {
                    transactionManager.rollback(transaction);
                    return false;
                }
            }

            throw new ServiceException(INVALID_REVIEW_MESSAGE);
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean removeReviewById(String id) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Long reviewId = Long.valueOf(id);
            if (reviewDao.removeConnectionBetweenReviewAndGood(reviewId) && reviewDao.removeById(reviewId)) {
                transactionManager.commit(transaction);
                return true;
            }

            transactionManager.rollback(transaction);
            return false;
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean updateReview(Review review) {
        if (reviewValidator.validate(review) && review.getId() != 0) {
            return reviewDao.update(review);
        }

        throw new ServiceException(INVALID_REVIEW_MESSAGE);
    }

    public boolean isCreatedReview(String userId, String goodId) {
        return !reviewDao.findReviewByUserAndGoodId(Long.parseLong(userId), Long.parseLong(goodId)).isPresent();
    }

    private Long findCurrentReviewId(Review review) {
        return Collections.max(reviewDao.findByUserId(review.getAuthor().getId()).stream()
                .map(Review::getId).collect(Collectors.toList()));
    }
}
