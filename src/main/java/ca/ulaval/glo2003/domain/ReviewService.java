package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.Review;
import ca.ulaval.glo2003.domain.factories.ReviewFactory;
import jakarta.ws.rs.NotFoundException;

public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewFactory reviewFactory;

    public ReviewService(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, ReviewFactory reviewFactory) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.reviewFactory = reviewFactory;
    }

    public String createReview(String restaurantId, Integer rating, String comment) {
        restaurantRepository.get(restaurantId).orElseThrow(() -> new NotFoundException("Restaurant does not exist"));

        Review review = reviewFactory.create(restaurantId, rating, comment);
        reviewRepository.add(review);

        return review.getId();
    }
}
