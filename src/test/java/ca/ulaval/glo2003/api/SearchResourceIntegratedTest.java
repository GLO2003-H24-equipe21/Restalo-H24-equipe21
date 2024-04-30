package ca.ulaval.glo2003.api;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.api.exceptions.ConstraintViolationExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.EntityNotFoundExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.IllegalArgumentExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.NullPointerExceptionMapper;
import ca.ulaval.glo2003.api.responses.ErrorResponse;
import ca.ulaval.glo2003.api.responses.ReviewResponse;
import ca.ulaval.glo2003.data.inmemory.ReservationRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.ReviewRepositoryInMemory;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.ReviewRepository;
import ca.ulaval.glo2003.domain.SearchService;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.entities.Review;
import ca.ulaval.glo2003.domain.entities.ReviewFixture;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SearchResourceIntegratedTest {

    private static JerseyTestApi api;

    static final RestaurantRepository restaurantRepository = new RestaurantRepositoryInMemory();
    static final ReservationRepository reservationRepository = new ReservationRepositoryInMemory();
    static final ReviewRepository reviewRepository = new ReviewRepositoryInMemory();

    static final SearchFactory searchFactory = new SearchFactory();

    static final SearchService searchService =
            new SearchService(
                    restaurantRepository, reservationRepository, reviewRepository, searchFactory);

    protected static Application configure() {
        return new ResourceConfig()
                .register(new SearchResource(searchService))
                .register(new NullPointerExceptionMapper())
                .register(new IllegalArgumentExceptionMapper())
                .register(new ConstraintViolationExceptionMapper())
                .register(new EntityNotFoundExceptionMapper());
    }

    @BeforeAll
    static void startServer() {
        api = new JerseyTestApi(configure());
        api.start();

        restaurantRepository.add(RESTAURANT);
        restaurantRepository.add(OTHER_RESTAURANT);
        reviewRepository.add(REVIEW);
        reviewRepository.add(OTHER_REVIEW);
    }

    @AfterAll
    static void stopServer() {
        api.stop();
    }

    @Test
    void whenSearchReviews_thenReturnsReviewsWith200() {
        Response response = api.path("/restaurants/" + RESTAURANT_ID + "/reviews").request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(new GenericType<List<ReviewResponse>>() {}))
                .containsExactly(REVIEW_RESPONSE, OTHER_REVIEW_RESPONSE);
    }

    @Test
    void whenSearchReviewsWithRating_thenReturnsCorrespondingReviewsWith200() {
        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .queryParam("rating", 5)
                        .request()
                        .get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(new GenericType<List<ReviewResponse>>() {}))
                .containsExactly(REVIEW_RESPONSE);
    }

    @Test
    void whenSearchReviewsWithRatings_thenReturnsCorrespondingReviewsWith200() {
        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .queryParam("rating", 5)
                        .queryParam("rating", 2)
                        .request()
                        .get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(new GenericType<List<ReviewResponse>>() {}))
                .containsExactly(REVIEW_RESPONSE, OTHER_REVIEW_RESPONSE);
    }

    @Test
    void whenSearchReviewsWithFrom_thenReturnsCorrespondingReviewsWith200() {
        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .queryParam("from", "2024-05-04")
                        .queryParam("to", "2025-05-05")
                        .request()
                        .get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(new GenericType<List<ReviewResponse>>() {}))
                .containsExactly(OTHER_REVIEW_RESPONSE);
    }

    @Test
    void givenInvalidRatingFormat_whenSearchReviews_thenReturnsInvalidParameterWith400() {
        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .queryParam("rating", "asdf")
                        .request()
                        .get();

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    void givenInvalidFromFormat_whenSearchReviews_thenReturnsInvalidParameterWith400() {
        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .queryParam("from", "asdf")
                        .request()
                        .get();

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    void givenInvalidToFormat_whenSearchReviews_thenReturnsInvalidParameterWith400() {
        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .queryParam("to", "asdf")
                        .request()
                        .get();

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    void givenInvalidRestaurantId_whenSearchReviews_thenReturnsNotFoundWith404() {
        Response response =
                api.path("/restaurants/" + NON_EXISTING_RESTAURANT_ID + "/reviews").request().get();

        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void givenRestaurantWithNoReviews_whenSearchReviews_thenReturnsNoReviewsWith200() {
        Response response =
                api.path("/restaurants/" + OTHER_RESTAURANT_ID + "/reviews").request().get();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(new GenericType<List<ReviewResponse>>() {})).isEmpty();
    }

    private static final String RESTAURANT_ID = UUID.randomUUID().toString();
    private static final String OTHER_RESTAURANT_ID = UUID.randomUUID().toString();
    private static final String NON_EXISTING_RESTAURANT_ID = "12345";
    private static final String MAY_1 = "2024-05-01";
    private static final String MAY_5 = "2024-05-05";

    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
    private static final Restaurant OTHER_RESTAURANT =
            new RestaurantFixture().withId(OTHER_RESTAURANT_ID).create();

    private static final Review REVIEW =
            new ReviewFixture()
                    .withRestaurantId(RESTAURANT_ID)
                    .withRating(5)
                    .withDate(LocalDate.parse(MAY_1))
                    .create();
    private static final Review OTHER_REVIEW =
            new ReviewFixture()
                    .withRestaurantId(RESTAURANT_ID)
                    .withRating(2)
                    .withDate(LocalDate.parse(MAY_5))
                    .create();

    private static final ReviewResponse REVIEW_RESPONSE =
            new ReviewResponse(REVIEW.getId(), 5, REVIEW.getComment(), MAY_1);
    private static final ReviewResponse OTHER_REVIEW_RESPONSE =
            new ReviewResponse(OTHER_REVIEW.getId(), 2, OTHER_REVIEW.getComment(), MAY_5);
}
