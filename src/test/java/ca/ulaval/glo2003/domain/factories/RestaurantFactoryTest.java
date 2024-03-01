package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantFactoryTest {
    private static final String OWNER_ID = "1234";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";

    RestaurantFactory restaurantFactory;
    @Mock
    RestaurantReservations restaurantReservations;
    @Mock
    RestaurantHours restaurantHours;

    private static final RestaurantReservationsFactory restaurantReservationsFactory = new RestaurantReservationsFactory();
    private static final RestaurantHoursFactory restaurantHoursFactory = new RestaurantHoursFactory();
    private static final RestaurantHours HOURS = restaurantHoursFactory.create(OPEN, CLOSE);
    private static final RestaurantReservations RESERVATIONS = restaurantReservationsFactory.create(120);

    @BeforeEach
    void setUp() {
        restaurantFactory = new RestaurantFactory();
    }

    @Test
    void canCreateRestaurantWithValidValues() {
        Restaurant restaurant = restaurantFactory.create(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                HOURS,
                RESERVATIONS
        );
        Assertions.assertThat(restaurant).isNotNull();
        Assertions.assertThat(restaurant.getOwnerId()).isEqualTo(OWNER_ID);
        Assertions.assertThat(restaurant.getName()).isEqualTo(RESTAURANT_NAME);
        Assertions.assertThat(restaurant.getCapacity()).isEqualTo(CAPACITY);
        Assertions.assertThat(restaurant.getHours()).isEqualTo(HOURS);
        Assertions.assertThat(restaurant.getReservations()).isEqualTo(RESERVATIONS);
    }

    @Test
    void EmptyName_createRestaurantShouldThrow() {
        String emptyName = "";
        assertThrows(IllegalArgumentException.class, () -> restaurantFactory.create(
                OWNER_ID,
                emptyName,
                CAPACITY,
                HOURS,
                RESERVATIONS
        ));
    }
    @Test
    void CapacityBelowOne_createRestaurantShouldThrow() {
        int belowOneCapacity = -2;
        assertThrows(IllegalArgumentException.class, () -> restaurantFactory.create(
                OWNER_ID,
                RESTAURANT_NAME,
                belowOneCapacity,
                HOURS,
                RESERVATIONS
        ));
    }


}