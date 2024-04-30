package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.entities.Review;
import ca.ulaval.glo2003.domain.entities.ReviewFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public abstract class ReviewRepositoryTest {
    protected abstract ReviewRepository createRepository();

    private ReviewRepository reviewRepository;

    Review review;
    Review otherReview;

    @BeforeEach
    void setUp() {
        reviewRepository = createRepository();

        review = new ReviewFixture().withRestaurantId(RESTAURANT_ID).withRating(5).withDate(MAY_1).create();
        otherReview = new ReviewFixture().withRestaurantId(RESTAURANT_ID).withRating(2).withDate(MAY_5).create();

        reviewRepository.add(review);
        reviewRepository.add(otherReview);
    }

    @Test
    void whenSearchReviewsWithNoParams_thenReturnsAllReviews() {
        List<Review> gottenReviews = reviewRepository.searchReviews(RESTAURANT_ID, List.of(), null, null);

        assertThat(gottenReviews).containsExactly(review, otherReview);
    }

    @Test
    void whenSearchReviewsWithRatings_thenReturnsReviewsWithTheseRatings() {
        List<Integer> ratings = List.of(1, 4, 5);

        List<Review> gottenReviews = reviewRepository.searchReviews(RESTAURANT_ID, ratings, null, null);

        assertThat(gottenReviews).containsExactly(review);
    }

    @Test
    void whenSearchReviewsWithFromDate_thenReturnsReviewsMadeAfterOrAtThisDate() {
        LocalDate from = LocalDate.parse("2024-05-03");

        List<Review> gottenReviews = reviewRepository.searchReviews(RESTAURANT_ID, List.of(), from, null);

        assertThat(gottenReviews).containsExactly(otherReview);
    }

    @Test
    void whenSearchReviewsWithToDate_thenReturnsReviewsMadeBeforeOrAtThisDate() {
        LocalDate to = LocalDate.parse("2024-05-01");

        List<Review> gottenReviews = reviewRepository.searchReviews(RESTAURANT_ID, List.of(), null, to);

        assertThat(gottenReviews).containsExactly(review);
    }

    private static final String RESTAURANT_ID = UUID.randomUUID().toString();
    private static final LocalDate MAY_1 = LocalDate.parse("2024-05-01");
    private static final LocalDate MAY_5 = LocalDate.parse("2024-05-05");
}
