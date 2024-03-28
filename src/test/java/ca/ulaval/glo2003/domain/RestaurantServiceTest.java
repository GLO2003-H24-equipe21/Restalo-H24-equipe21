package ca.ulaval.glo2003.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.factories.RestaurantConfigurationFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    private static final String OWNER_ID = "1234";
    private static final String INVALID_OWNER_ID = "ABCD";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;
    private static String restaurantId;
    private static final Restaurant restaurant =
            new Restaurant(
                    OWNER_ID,
                    RESTAURANT_NAME,
                    CAPACITY,
                    new RestaurantHours(LocalTime.parse(OPEN), LocalTime.parse(CLOSE)),
                    new RestaurantConfiguration(DURATION));
    private static List<Restaurant> restaurants;

    RestaurantService restaurantService;
    @Mock RestaurantFactory restaurantFactory;
    @Mock RestaurantRepositoryInMemory restaurantRepository;
    @Mock RestaurantConfiguration restaurantConfiguration;
    @Mock RestaurantHours restaurantHours;
    @Mock RestaurantConfigurationFactory restaurantConfigurationFactory;
    @Mock RestaurantHoursFactory restaurantHoursFactory;
    @Mock SearchService searchService;

    Restaurant restaurantMock;
    RestaurantHoursPojo restaurantHoursPojo;
    RestaurantConfigurationPojo restaurantConfigurationPojo;

    @BeforeEach
    void setUp() {
        restaurantHours = RestaurantTestUtils.createRestaurantHours(OPEN, CLOSE);
        restaurantHoursPojo = new RestaurantHoursPojo(OPEN, CLOSE);
        restaurantConfiguration = RestaurantTestUtils.createRestaurantReservation(DURATION);
        restaurantConfigurationPojo = new RestaurantConfigurationPojo(DURATION);

        restaurantMock =
                new Restaurant(
                        OWNER_ID,
                        RESTAURANT_NAME,
                        CAPACITY,
                        restaurantHours,
                        restaurantConfiguration);
        restaurantId = restaurant.getId();

        restaurantHoursFactory = new RestaurantHoursFactory();
        restaurantConfigurationFactory = new RestaurantConfigurationFactory();

        restaurantService =
                new RestaurantService(
                        restaurantRepository,
                        restaurantFactory,
                        restaurantHoursFactory,
                        restaurantConfigurationFactory);

        searchService = new SearchService(restaurantRepository, null);

        restaurants = restaurantRepository.getByOwnerId(OWNER_ID);
    }

    @Test
    void givenValidInputs_whenCreate_thenRestaurantCreated() {
        when(restaurantFactory.create(
                        OWNER_ID,
                        RESTAURANT_NAME,
                        CAPACITY,
                        restaurantHours,
                        restaurantConfiguration))
                .thenReturn(restaurantMock);

        String restaurantServiceId =
                restaurantService.createRestaurant(
                        OWNER_ID,
                        RESTAURANT_NAME,
                        CAPACITY,
                        restaurantHoursPojo,
                        restaurantConfigurationPojo);

        assertEquals(restaurantMock.getId(), restaurantServiceId);
    }

    @Test
    void whenCreateRestaurantWithValidValues_thenRestaurantIsCreatedAndSaved() {
        when(restaurantFactory.create(
                        OWNER_ID,
                        RESTAURANT_NAME,
                        CAPACITY,
                        restaurantHours,
                        restaurantConfiguration))
                .thenReturn(restaurantMock);

        restaurantService.createRestaurant(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHoursPojo,
                restaurantConfigurationPojo);

        verify(restaurantRepository).add(restaurantMock);
    }

    @Test
    void givenExistingId_thenFindsRestaurant() {
        when(restaurantRepository.get(restaurantId)).thenReturn(restaurant);

        Restaurant gottenRestaurant = restaurantService.getRestaurant(restaurantId, OWNER_ID);

        Assertions.assertThat(gottenRestaurant).isEqualTo(restaurant);
    }

    @Test
    void givenInvalidId_thenThrowNotFoundException() {
        when(restaurantRepository.get("invalid_number")).thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> restaurantService.getRestaurant("invalid_number", OWNER_ID));
    }

    @Test
    void givenValidOwnerId_thenListRestaurantsReturnsListOfRestaurantDtos() {
        List<Restaurant> mockRestaurants =
                Arrays.asList(
                        new Restaurant(
                                OWNER_ID,
                                RESTAURANT_NAME,
                                CAPACITY,
                                restaurantHours,
                                restaurantConfiguration));
        when(restaurantRepository.getByOwnerId(OWNER_ID)).thenReturn(mockRestaurants);
        when(restaurantFactory.create(
                        OWNER_ID,
                        RESTAURANT_NAME,
                        CAPACITY,
                        restaurantHours,
                        restaurantConfiguration))
                .thenReturn(restaurant);

        restaurantService.createRestaurant(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHoursPojo,
                restaurantConfigurationPojo);
        List<Restaurant> restaurants = restaurantService.listRestaurants(OWNER_ID);

        Assertions.assertThat(mockRestaurants.size()).isEqualTo(restaurants.size());
    }

    @Test
    void givenInvalidOwnerId_thenReturnsEmptyRestaurantList() {

        when(restaurantRepository.getByOwnerId(INVALID_OWNER_ID))
                .thenReturn(Collections.emptyList());
        List<Restaurant> restaurants = restaurantService.listRestaurants(INVALID_OWNER_ID);

        assertTrue(restaurants.isEmpty());
    }
}
