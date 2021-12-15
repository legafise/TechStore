package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Review;
import by.lashkevich.logic.entity.User;

/**
 * The type Review validator.
 * @author Roman Lashkevich
 */
public class ReviewValidator {
    public boolean validate(Review review) {
        return review != null && validateAuthor(review.getAuthor()) && validateContent(review.getContent())
                && validateRate(review.getRate());
    }

    private boolean validateAuthor(User author) {
        return author != null && author.getId() != 0;
    }

    private boolean validateContent(String reviewContent) {
        return reviewContent != null && reviewContent.length() > 2 && reviewContent.length() <= 600;
    }

    private boolean validateRate(short rate) {
        return rate > 0 && rate <= 5;
    }
}
