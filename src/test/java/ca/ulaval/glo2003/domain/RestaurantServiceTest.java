package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantReservationsFactory;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    private static final String OWNER_ID = "1234";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;
    private static String restaurantId;

    RestaurantService restaurantService;
    @Mock
    RestaurantFactory restaurantFactory;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    RestaurantReservations restaurantReservations;
    @Mock
    RestaurantHours restaurantHours;
    @Mock
    RestaurantReservationsFactory restaurantReservationsFactory;
    @Mock
    RestaurantHoursFactory restaurantHoursFactory;

    Restaurant restaurant;
    RestaurantDto restaurantDto;
    RestaurantHoursDto restaurantHoursDto;
    RestaurantReservationsDto restaurantReservationsDto;
    RestaurantMapper restaurantMapper = new RestaurantMapper();

    @BeforeEach
    void setUp() {
        restaurantHours = RestaurantTestUtils.createRestaurantHours(OPEN, CLOSE);
        restaurantHoursDto = RestaurantTestUtils.createRestaurantHoursDTO(OPEN, CLOSE);
        restaurantReservations = RestaurantTestUtils.createRestaurantReservation(DURATION);
        restaurantReservationsDto = RestaurantTestUtils.createRestaurantReservationsDTO(DURATION);

        restaurant = new Restaurant(OWNER_ID, RESTAURANT_NAME, CAPACITY, restaurantHours, restaurantReservations);
        restaurantId = restaurant.getId();

        restaurantDto = new RestaurantDto();
        restaurantDto.ownerId = OWNER_ID;
        restaurantDto.name = RESTAURANT_NAME;
        restaurantDto.capacity = CAPACITY;
        restaurantDto.hours = restaurantHoursDto;
        restaurantDto.reservations = restaurantReservationsDto;

        restaurantHoursFactory = new RestaurantHoursFactory();
        restaurantReservationsFactory = new RestaurantReservationsFactory();

        restaurantService = new RestaurantService(
                restaurantRepository,
                restaurantFactory,
                restaurantHoursFactory,
                restaurantReservationsFactory
        );

    }

    @Test
    void whenCreateRestaurantWithValidValues_thenRestaurantIsCreated() {
        when(restaurantFactory.create(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHours,
                restaurantReservations
        )).thenReturn(restaurant);
        String restaurantServiceId = restaurantService.createRestaurant(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHoursDto,
                restaurantReservationsDto
        );
        assertEquals(restaurantId, restaurantServiceId);
    }
    @Test
    void whenCreateRestaurantWithValidValues_thenRestaurantIsCreatedAndSaved() {
        when(restaurantFactory.create(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHours,
                restaurantReservations
        )).thenReturn(restaurant);
        restaurantService.createRestaurant(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHoursDto,
                restaurantReservationsDto
        );
        verify(restaurantRepository).add(restaurant);
    }

    @Test
    void givenExistingId_thenFindsRestaurant() {
        when(restaurantRepository.get(restaurantId)).thenReturn(restaurant);
        RestaurantDto gottenRestaurant = restaurantService.getRestaurant(restaurantId, OWNER_ID);

        Assertions.assertThat(gottenRestaurant.id).isEqualTo(restaurantMapper.toDto(restaurant).id);
        Assertions.assertThat(gottenRestaurant.name).isEqualTo(restaurantMapper.toDto(restaurant).name);
        Assertions.assertThat(gottenRestaurant.capacity).isEqualTo(restaurantMapper.toDto(restaurant).capacity);
        Assertions.assertThat(gottenRestaurant.hours).isEqualTo(restaurantMapper.toDto(restaurant).hours);
        Assertions.assertThat(gottenRestaurant.reservations).isEqualTo(restaurantMapper.toDto(restaurant).reservations);
    }
    @Test
    void givenInvalidId_thenThrowNullPointerException() {
        when(restaurantRepository.get("invalid_number")).thenReturn(null);
        assertThrows(NullPointerException.class,
                () -> restaurantService.getRestaurant("invalid_number", OWNER_ID));
    }


}