package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.entities.Review;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReviewFactoryTest {
    ReviewFactory reviewFactory;

    @BeforeEach
    void setUp() {
        this.reviewFactory = new ReviewFactory();
    }

    @Test
    void canCreateReview() {
        Review review = reviewFactory.create(RESTAURANT_ID, RATING, COMMENT);

        assertThat(review.getRestaurantId()).isEqualTo(RESTAURANT_ID);
        assertThat(review.getRating()).isEqualTo(RATING);
        assertThat(review.getComment()).isEqualTo(COMMENT);
        assertThat(review.getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void whenRatingIsBelow0_thenThrowsIllegalArgumentException() {
        int belowZeroRating = -1;

        assertThrows(
                IllegalArgumentException.class,
                () -> reviewFactory.create(RESTAURANT_ID, belowZeroRating, COMMENT));
    }

    @Test
    void whenRatingIsAbove5_thenThrowsIllegalArgumentException() {
        int aboveFiveRating = 6;

        assertThrows(
                IllegalArgumentException.class,
                () -> reviewFactory.create(RESTAURANT_ID, aboveFiveRating, COMMENT));
    }

    @Test
    void whenCommentIsEmpty_thenThrowsIllegalArgumentException() {
        String emptyComment = "";

        assertThrows(
                IllegalArgumentException.class,
                () -> reviewFactory.create(RESTAURANT_ID, RATING, emptyComment));
    }

    private static final String RESTAURANT_ID = "restaurant_id";
    private static final Integer RATING = 4;
    private static final String COMMENT = "Very good restaurant!";
}
