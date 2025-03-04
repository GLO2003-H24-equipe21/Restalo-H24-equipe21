package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.pojos.SearchOpenedPojo;
import ca.ulaval.glo2003.domain.entities.*;
import ca.ulaval.glo2003.domain.exceptions.EntityNotFoundException;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    SearchService searchService;

    @Mock RestaurantRepository restaurantRepository;
    @Mock ReservationRepository reservationRepository;
    @Mock ReviewRepository reviewRepository;
    @Mock SearchFactory searchFactory;

    @BeforeEach
    void setup() {
        searchService =
                new SearchService(
                        restaurantRepository,
                        reservationRepository,
                        reviewRepository,
                        searchFactory);
    }

    @Test
    void whenSearchRestaurants_thenReturnsRestaurantList() {
        when(searchFactory.create(RESTAURANT_NAME, OPENED_FROM, OPENED_TO)).thenReturn(SEARCH);
        when(restaurantRepository.searchRestaurants(SEARCH)).thenReturn(RESTAURANTS);

        List<Restaurant> gottenRestaurants =
                searchService.searchRestaurants(RESTAURANT_NAME, SEARCH_OPENED_POJO);

        assertThat(gottenRestaurants).isEqualTo(RESTAURANTS);
    }

    @Test
    void givenNullSearchOpened_whenSearchRestaurants_thenReturnsRestaurantList() {
        when(searchFactory.create(RESTAURANT_NAME, null, null)).thenReturn(SEARCH_NULL);
        when(restaurantRepository.searchRestaurants(SEARCH_NULL)).thenReturn(RESTAURANTS);

        List<Restaurant> gottenRestaurants = searchService.searchRestaurants(RESTAURANT_NAME, null);

        assertThat(gottenRestaurants).isEqualTo(RESTAURANTS);
    }

    @Test
    void givenEmptyRepository_whenSearchRestaurants_thenReturnsEmptyList() {
        when(searchFactory.create(RESTAURANT_NAME, OPENED_FROM, OPENED_TO)).thenReturn(SEARCH);
        when(restaurantRepository.searchRestaurants(SEARCH)).thenReturn(Collections.emptyList());

        List<Restaurant> gottenRestaurants =
                searchService.searchRestaurants(RESTAURANT_NAME, SEARCH_OPENED_POJO);

        assertThat(gottenRestaurants).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenSearchReservations_thenReturnsReservationList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.searchReservations(
                        RESTAURANT_ID, LocalDate.parse(DATE), CUSTOMER_NAME))
                .thenReturn(RESERVATIONS);

        List<Reservation> gottenReservations =
                searchService.searchReservations(RESTAURANT_ID, OWNER_ID, DATE, CUSTOMER_NAME);

        assertThat(gottenReservations).isEqualTo(RESERVATIONS);
    }

    @Test
    void givenRestaurantWithNoReservations_whenSearchReservations_thenReturnsEmptyList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.searchReservations(
                        RESTAURANT_ID, LocalDate.parse(DATE), CUSTOMER_NAME))
                .thenReturn(Collections.emptyList());

        List<Reservation> gottenReservations =
                searchService.searchReservations(RESTAURANT_ID, OWNER_ID, DATE, CUSTOMER_NAME);

        assertThat(gottenReservations).isEqualTo(Collections.emptyList());
    }

    @Test
    void givenNonExistingRestaurant_whenSearchReservations_thenThrowsEntityNotFoundException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () ->
                        searchService.searchReservations(
                                RESTAURANT_ID, OWNER_ID, DATE, CUSTOMER_NAME));
    }

    @Test
    void givenInvalidOwnerId_whenSearchReservations_thenThrowsEntityNotFoundException() {
        String invalidOwnerId = "invalid_owner";
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));

        assertThrows(
                EntityNotFoundException.class,
                () ->
                        searchService.searchReservations(
                                RESTAURANT_ID, invalidOwnerId, DATE, CUSTOMER_NAME));
    }

    @Test
    void whenSearchAvailabilities_thenReturnsAvailabilityList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.searchAvailabilities(RESTAURANT, LocalDate.parse(DATE)))
                .thenReturn(AVAILABILITIES);

        List<Availability> gottenAvailabilities =
                searchService.searchAvailabilities(RESTAURANT_ID, DATE);

        assertThat(gottenAvailabilities).isEqualTo(AVAILABILITY_ENTITIES);
    }

    @Test
    void givenNonExistingRestaurant_whenSearchAvailabilities_thenThrowsEntityNotFoundException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> searchService.searchAvailabilities(RESTAURANT_ID, DATE));
    }

    @Test
    void whenSearchReviews_thenReturnsReviewList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reviewRepository.searchReviews(
                        RESTAURANT_ID, RATINGS, LocalDate.parse(FROM), LocalDate.parse(TO)))
                .thenReturn(REVIEWS);

        List<Review> gottenReviews =
                searchService.searchReviews(RESTAURANT_ID, RATINGS_STRING, FROM, TO);

        assertThat(gottenReviews).isEqualTo(REVIEWS);
    }

    @Test
    void givenRestaurantWithNoReviews_whenSearchReviews_thenReturnsEmptyList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reviewRepository.searchReviews(
                        RESTAURANT_ID, RATINGS, LocalDate.parse(FROM), LocalDate.parse(TO)))
                .thenReturn(List.of());

        List<Review> gottenReviews =
                searchService.searchReviews(RESTAURANT_ID, RATINGS_STRING, FROM, TO);

        assertThat(gottenReviews).isEmpty();
    }

    @Test
    void givenNonExistingRestaurant_whenSearchReviews_thenThrowsEntityNotFoundException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> searchService.searchReviews(RESTAURANT_ID, RATINGS_STRING, FROM, TO));
    }

    @Test
    void givenInvalidRatingFormat_whenSearchReviews_thenThrowsInvalidArgumentException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        List<String> invalidRatings = List.of("1,2", "asdf");

        assertThrows(
                IllegalArgumentException.class,
                () -> searchService.searchReviews(RESTAURANT_ID, invalidRatings, FROM, TO));
    }

    @Test
    void givenRatingBelow0_whenSearchReviews_thenThrowsInvalidArgumentException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        List<String> ratingBelow0 = List.of("-1");

        assertThrows(
                IllegalArgumentException.class,
                () -> searchService.searchReviews(RESTAURANT_ID, ratingBelow0, FROM, TO));
    }

    @Test
    void givenRatingAbove5_whenSearchReviews_thenThrowsInvalidArgumentException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        List<String> ratingAbove5 = List.of("5", "6");

        assertThrows(
                IllegalArgumentException.class,
                () -> searchService.searchReviews(RESTAURANT_ID, ratingAbove5, FROM, TO));
    }

    @Test
    void givenInvalidFromFormat_whenSearchReviews_thenThrowsInvalidArgumentException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        String invalidFrom = "january 16th";

        assertThrows(
                IllegalArgumentException.class,
                () -> searchService.searchReviews(RESTAURANT_ID, RATINGS_STRING, invalidFrom, TO));
    }

    @Test
    void givenInvalidToFormat_whenSearchReviews_thenThrowsInvalidArgumentException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        String invalidTo = "2023-25-25";

        assertThrows(
                IllegalArgumentException.class,
                () -> searchService.searchReviews(RESTAURANT_ID, RATINGS_STRING, FROM, invalidTo));
    }

    private static final String RESTAURANT_NAME = "restaurant";
    private static final String OPENED_FROM = "10:00:00";
    private static final String OPENED_TO = "18:30:00";

    private static final SearchOpened SEARCH_OPENED =
            new SearchOpened(LocalTime.parse(OPENED_FROM), LocalTime.parse(OPENED_TO));
    private static final SearchOpenedPojo SEARCH_OPENED_POJO =
            new SearchOpenedPojo(OPENED_FROM, OPENED_TO);
    private static final Search SEARCH = new Search(RESTAURANT_NAME, SEARCH_OPENED);
    private static final Search SEARCH_NULL = new Search(null, new SearchOpened(null, null));

    private static final String RESTAURANT_ID = "123abc123abc";
    private static final String OWNER_ID = "owner";
    private static final String DATE = LocalDate.now().plusDays(2).toString();
    private static final String CUSTOMER_NAME = "Johnny Cash";

    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
    private static final List<Restaurant> RESTAURANTS = TestUtils.createRestaurants(5);

    private static final List<Reservation> RESERVATIONS =
            TestUtils.createReservations(5, RESTAURANT_ID);

    private static final Map<LocalDateTime, Integer> AVAILABILITIES =
            new AvailabilitiesFixture().create();
    private static final List<Availability> AVAILABILITY_ENTITIES =
            new AvailabilitiesFixture().createList();

    private static final List<Integer> RATINGS = List.of(1, 2, 3, 4, 5);
    private static final List<String> RATINGS_STRING = List.of("1", "2", "3", "4", "5");
    private static final String FROM = LocalDate.now().minusDays(2).toString();
    private static final String TO = LocalDate.now().plusDays(2).toString();

    private static final List<Review> REVIEWS = TestUtils.createReviews(5, RESTAURANT_ID);
}
