package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Review;
import java.time.LocalDate;
import java.util.UUID;

public class ReviewFactory {
    public Review create(String restaurantId, Integer rating, String comment) {
        verifyRatingBetween0And5(rating);
        verifyCommentNotEmpty(comment);

        return new Review(
                UUID.randomUUID().toString(), restaurantId, rating, comment, LocalDate.now());
    }

    private void verifyRatingBetween0And5(Integer rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Review rating must be between 0 and 5");
        }
    }

    private void verifyCommentNotEmpty(String comment) {
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("Review comment must not be empty");
        }
    }
}
