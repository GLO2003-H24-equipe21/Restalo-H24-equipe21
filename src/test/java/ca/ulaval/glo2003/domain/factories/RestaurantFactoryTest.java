package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantFactoryTest {
    private static final String OWNER_ID = "1234";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;

    RestaurantFactory restaurantFactory;
    @Mock
    RestaurantReservations restaurantReservations;
    @Mock
    RestaurantHours restaurantHours;
    @Mock
    RestaurantReservationsFactory restaurantReservationsFactory;
    @Mock
    RestaurantHoursFactory restaurantHoursFactory;

    @BeforeEach
    void setUp() {
        restaurantFactory = new RestaurantFactory();
        lenient().when(restaurantHoursFactory.create(OPEN, CLOSE)).thenReturn(restaurantHours);
        lenient().when(restaurantReservationsFactory.create(DURATION)).thenReturn(restaurantReservations);
    }

    @Test
    void canCreateRestaurantWithValidValues() {
        Restaurant restaurant = restaurantFactory.create(
                OWNER_ID,
                RESTAURANT_NAME,
                CAPACITY,
                restaurantHours,
                restaurantReservations
        );
        Assertions.assertThat(restaurant).isNotNull();
        Assertions.assertThat(restaurant.getOwnerId()).isEqualTo(OWNER_ID);
        Assertions.assertThat(restaurant.getName()).isEqualTo(RESTAURANT_NAME);
        Assertions.assertThat(restaurant.getCapacity()).isEqualTo(CAPACITY);
        Assertions.assertThat(restaurant.getHours()).isEqualTo(restaurantHours);
        Assertions.assertThat(restaurant.getReservations()).isEqualTo(restaurantReservations);
    }

    @Test
    void EmptyName_createRestaurantShouldThrow() {
        String emptyName = "";
        assertThrows(IllegalArgumentException.class, () -> restaurantFactory.create(
                OWNER_ID,
                emptyName,
                CAPACITY,
                restaurantHours,
                restaurantReservations
        ));
    }
    @Test
    void CapacityBelowOne_createRestaurantShouldThrow() {
        int belowOneCapacity = -2;
        assertThrows(IllegalArgumentException.class, () -> restaurantFactory.create(
                OWNER_ID,
                RESTAURANT_NAME,
                belowOneCapacity,
                restaurantHours,
                restaurantReservations
        ));
    }


}