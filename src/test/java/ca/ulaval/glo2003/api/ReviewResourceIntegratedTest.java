package ca.ulaval.glo2003.api;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.api.exceptions.ConstraintViolationExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.EntityNotFoundExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.IllegalArgumentExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.NullPointerExceptionMapper;
import ca.ulaval.glo2003.api.requests.CreateReviewRequest;
import ca.ulaval.glo2003.api.responses.ErrorResponse;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.ReviewRepositoryInMemory;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.ReviewRepository;
import ca.ulaval.glo2003.domain.ReviewService;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.factories.ReviewFactory;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReviewResourceIntegratedTest {

    private static JerseyTestApi api;

    static final ReviewRepository reviewRepository = new ReviewRepositoryInMemory();
    static final RestaurantRepository restaurantRepository = new RestaurantRepositoryInMemory();
    static final ReviewFactory reviewFactory = new ReviewFactory();

    static final ReviewService reviewService =
            new ReviewService(reviewRepository, restaurantRepository, reviewFactory);

    protected static Application configure() {
        return new ResourceConfig()
                .register(new ReviewResource(reviewService))
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
    }

    @AfterAll
    static void stopServer() {
        api.stop();
    }

    @Test
    void whenCreateReview_thenReturnsCreatedWith201() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.rating = RATING;
        request.comment = COMMENT;

        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    void givenNoRating_whenCreateReview_thenReturnsMissingParameterWith400() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.comment = COMMENT;

        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("MISSING_PARAMETER");
    }

    @Test
    void givenNoComment_whenCreateReview_thenReturnsMissingParameterWith400() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.rating = RATING;

        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("MISSING_PARAMETER");
    }

    @Test
    void givenRatingBelow0_whenCreateReview_thenReturnsInvalidParameterWith400() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.rating = BELOW_ZERO_RATING;
        request.comment = COMMENT;

        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    void givenRatingAbove5_whenCreateReview_thenReturnsInvalidParameterWith400() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.rating = ABOVE_FIVE_RATING;
        request.comment = COMMENT;

        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    void givenEmptyComment_whenCreateReview_thenReturnsInvalidParameterWith400() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.rating = RATING;
        request.comment = EMPTY_COMMENT;

        Response response =
                api.path("/restaurants/" + RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo("INVALID_PARAMETER");
    }

    @Test
    void givenNonExistingRestaurantId_whenCreateReview_thenReturnsNotFoundWith404() {
        CreateReviewRequest request = new CreateReviewRequest();
        request.rating = RATING;
        request.comment = EMPTY_COMMENT;

        Response response =
                api.path("/restaurants/" + NON_EXISTING_RESTAURANT_ID + "/reviews")
                        .request()
                        .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus()).isEqualTo(404);
    }

    private static final Integer RATING = 4;
    private static final String COMMENT = "Very good restaurant!";
    private static final String RESTAURANT_ID = UUID.randomUUID().toString();

    private static final Integer BELOW_ZERO_RATING = -2;
    private static final Integer ABOVE_FIVE_RATING = 10;
    private static final String EMPTY_COMMENT = "";
    private static final String NON_EXISTING_RESTAURANT_ID = "12345";

    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
}
