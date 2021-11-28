package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.ReviewDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.entity.Review;
import by.lashkevich.logic.service.ReviewService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.ReviewValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JWDReviewService implements ReviewService {
    private static final String NONEXISTENT_REVIEW_ID_MESSAGE = "Nonexistent review id was received";
    private static final String INVALID_REVIEW_MESSAGE = "Invalid review was received";
    private final Predicate<Review> reviewValidator;
    private final ReviewDao reviewDao;

    public JWDReviewService() {
        reviewDao = (ReviewDao) DaoFactory.REVIEW_DAO.getDao();
        reviewValidator = new ReviewValidator();
    }

    @Override
    public List<Review> findAllReviews() throws ServiceException {
        try {
            return reviewDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Review findReviewById(String id) throws ServiceException {
        try {
            Optional<Review> reviewOptional = reviewDao.findById(Long.parseLong(id));

            if (reviewOptional.isPresent()) {
                return reviewOptional.get();
            }

            throw new ServiceException(NONEXISTENT_REVIEW_ID_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean addReview(Review review, String goodId) throws ServiceException {
        Transaction transaction = TransactionFactory.getInstance().createTransaction();
        try {
            if (checkReviewForDuplication(String.valueOf(review.getAuthor().getId()), goodId)
                    && reviewValidator.test(review) && reviewDao.add(review)) {
                Long reviewId = findCurrentReviewId(review);
                if (reviewDao.connectReviewToGood(reviewId, Long.parseLong(goodId))) {
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    return false;
                }
            }

            throw new ServiceException(INVALID_REVIEW_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public boolean removeReviewById(String id) throws ServiceException {
        Transaction transaction = TransactionFactory.getInstance().createTransaction();
        try {
            Long reviewId = Long.valueOf(id);
            if (reviewDao.removeConnectionBetweenReviewAndGood(reviewId) && reviewDao.removeById(reviewId)) {
                transaction.commit();
                return true;
            }

            transaction.rollback();
            return false;
        } catch (DaoException | NumberFormatException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public boolean updateReview(Review review) throws ServiceException {
        try {
            if (reviewValidator.test(review) && review.getId() != 0) {
                return reviewDao.update(review);
            }

            throw new ServiceException(INVALID_REVIEW_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean checkReviewForDuplication(String userId, String goodId) {
        try {
            return !reviewDao.findReviewByUserAndGoodId(Long.parseLong(userId), Long.parseLong(goodId)).isPresent();
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e);
        }
    }

    private Long findCurrentReviewId(Review review) {
        return Collections.max(reviewDao.findByUserId(review.getAuthor().getId()).stream()
                .map(Review::getId).collect(Collectors.toList()));
    }
}
