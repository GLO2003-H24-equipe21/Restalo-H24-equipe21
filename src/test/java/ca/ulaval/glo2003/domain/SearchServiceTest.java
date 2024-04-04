package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.pojos.SearchOpenedPojo;
import ca.ulaval.glo2003.data.inmemory.ReservationRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantTestUtils;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.entities.SearchOpened;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
    private static final String RESTAURANT_NAME = "restaurant";
    private static final String OPENED_FROM = "10:00:00";
    private static final String OPENED_TO = "18:30:00";

    SearchService searchService;

    @Mock RestaurantRepositoryInMemory restaurantRepository;
    @Mock ReservationRepositoryInMemory reservationRepository;

    @Mock SearchFactory searchFactory;

    SearchOpened searchOpened;
    SearchOpenedPojo searchOpenedPojo;

    Search searchNonNull;
    Search searchNull;

    List<Restaurant> restaurants;

    @BeforeEach
    void setup() {
        searchOpened = new SearchOpened(LocalTime.parse(OPENED_FROM), LocalTime.parse(OPENED_TO));
        searchOpenedPojo = new SearchOpenedPojo(OPENED_FROM, OPENED_TO);

        searchNonNull = new Search(RESTAURANT_NAME, searchOpened);
        searchNull = new Search(null, new SearchOpened(null, null));

        restaurants = RestaurantTestUtils.createRestaurants(5);

        searchService =
                new SearchService(restaurantRepository, reservationRepository, searchFactory);
    }

    @Test
    public void givenNonNullInputs_whenSearchRestaurants_thenReturnsRestaurantDtoList() {
        when(searchFactory.create(RESTAURANT_NAME, OPENED_FROM, OPENED_TO))
                .thenReturn(searchNonNull);
        when(restaurantRepository.searchRestaurants(searchNonNull)).thenReturn(restaurants);

        List<Restaurant> gottenRestaurants =
                searchService.searchRestaurants(RESTAURANT_NAME, searchOpenedPojo);

        assertThat(gottenRestaurants).isEqualTo(restaurants);
    }

    @Test
    public void givenNullSearchOpenedDto_whenSearchRestaurants_thenReturnsRestaurantDtoList() {
        when(searchFactory.create(RESTAURANT_NAME, null, null)).thenReturn(searchNull);
        when(restaurantRepository.searchRestaurants(searchNull)).thenReturn(restaurants);

        List<Restaurant> gottenRestaurants = searchService.searchRestaurants(RESTAURANT_NAME, null);

        assertThat(gottenRestaurants).isEqualTo(restaurants);
    }

    @Test
    public void givenEmptyRepository_whenSearchRestaurants_thenReturnsEmptyDtoList() {
        when(searchFactory.create(RESTAURANT_NAME, OPENED_FROM, OPENED_TO))
                .thenReturn(searchNonNull);
        when(restaurantRepository.searchRestaurants(searchNonNull))
                .thenReturn(Collections.emptyList());

        List<Restaurant> gottenRestaurants =
                searchService.searchRestaurants(RESTAURANT_NAME, searchOpenedPojo);

        assertThat(gottenRestaurants).isEqualTo(Collections.emptyList());
    }
}
