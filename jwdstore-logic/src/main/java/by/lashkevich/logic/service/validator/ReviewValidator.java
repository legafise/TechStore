package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Review;
import by.lashkevich.logic.entity.User;

import java.util.function.Predicate;

/**
 * The type Review validator.
 * @author Roman Lashkevich
 */
public class ReviewValidator implements Predicate<Review> {
    @Override
    public boolean test(Review review) {
        return review != null && validateAuthor(review.getAuthor()) && validateContent(review.getContent())
                && validateRate(review.getRate());
    }

    private boolean validateAuthor(User author) {
        return author != null && author.getId() != 0;
    }

    private boolean validateContent(String reviewContent) {
        return reviewContent != null && reviewContent.length() > 2 && reviewContent.length() < 400;
    }

    private boolean validateRate(short rate) {
        return rate > 0 && rate <= 5;
    }
}
