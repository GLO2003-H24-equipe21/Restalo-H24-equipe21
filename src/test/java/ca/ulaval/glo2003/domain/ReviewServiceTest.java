package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.entities.Review;
import ca.ulaval.glo2003.domain.entities.ReviewFixture;
import ca.ulaval.glo2003.domain.factories.ReviewFactory;
import jakarta.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    ReviewService reviewService;

    @Mock ReviewRepository reviewRepository;
    @Mock RestaurantRepository restaurantRepository;
    @Mock ReviewFactory reviewFactory;

    @BeforeEach
    void setUp() {
        this.reviewService =
                new ReviewService(reviewRepository, restaurantRepository, reviewFactory);
    }

    @Test
    void whenCreateReview_thenReturnsReviewId() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reviewFactory.create(RESTAURANT_ID, RATING, COMMENT)).thenReturn(REVIEW);

        String gottenId = reviewService.createReview(RESTAURANT_ID, RATING, COMMENT);

        assertThat(gottenId).isEqualTo(ID);
    }

    @Test
    void whenCreateReview_thenReviewIsSaved() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reviewFactory.create(RESTAURANT_ID, RATING, COMMENT)).thenReturn(REVIEW);

        reviewService.createReview(RESTAURANT_ID, RATING, COMMENT);

        verify(reviewRepository).add(REVIEW);
    }

    @Test
    void givenNonExistingRestaurantId_whenCreateReview_thenThrowsNotFoundException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> reviewService.createReview(RESTAURANT_ID, RATING, COMMENT));
    }

    private static final String ID = UUID.randomUUID().toString();
    private static final Integer RATING = 4;
    private static final String COMMENT = "Very good restaurant!";
    private static final String RESTAURANT_ID = UUID.randomUUID().toString();

    private static final Review REVIEW =
            new ReviewFixture().withId(ID).withRestaurantId(RESTAURANT_ID).create();
    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
}
