package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.Review;
import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository {

    void add(Review review);

    List<Review> searchReviews(
            String restaurantId, List<Integer> ratings, LocalDate from, LocalDate to);
}
