package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReviewRepository;
import ca.ulaval.glo2003.domain.entities.Review;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReviewRepositoryInMemory implements ReviewRepository {

    private HashMap<String, List<Review>> restaurantIdToReviews;

    public ReviewRepositoryInMemory() {
        restaurantIdToReviews = new HashMap<>();
    }

    @Override
    public void add(Review review) {}

    @Override
    public List<Review> searchReviews(
            String restaurantId, List<Integer> ratings, LocalDate from, LocalDate to) {
        return restaurantIdToReviews.getOrDefault(restaurantId, new ArrayList<>()).stream()
                .filter(review -> matchesRating(review.getRating(), ratings))
                .filter(review -> matchesDateFrom(review.getDate(), from))
                .filter(review -> matchesDateTo(review.getDate(), to))
                .collect(Collectors.toList());
    }

    private boolean matchesRating(Integer rating, List<Integer> ratings) {
        if (ratings.isEmpty()) return true;
        return ratings.contains(rating);
    }

    private boolean matchesDateFrom(LocalDate date, LocalDate from) {
        if (Objects.isNull(from)) return true;
        return !date.isBefore(from);
    }

    private boolean matchesDateTo(LocalDate date, LocalDate to) {
        if (Objects.isNull(to)) return true;
        return !date.isAfter(to);
    }
}
