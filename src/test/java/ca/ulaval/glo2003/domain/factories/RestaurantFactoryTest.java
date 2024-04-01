package ca.ulaval.glo2003.domain.factories;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantFactoryTest {
    private static final String OWNER_ID = "1234";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;

    RestaurantFactory restaurantFactory;
    @Mock RestaurantConfiguration restaurantConfiguration;
    @Mock RestaurantHours restaurantHours;
    @Mock RestaurantConfigurationFactory restaurantConfigurationFactory;
    @Mock RestaurantHoursFactory restaurantHoursFactory;

    @BeforeEach
    void setUp() {
        restaurantFactory = new RestaurantFactory();
        lenient().when(restaurantHoursFactory.create(OPEN, CLOSE)).thenReturn(restaurantHours);
        lenient()
                .when(restaurantConfigurationFactory.create(DURATION))
                .thenReturn(restaurantConfiguration);
    }

    @Test
    void givenValidInputs_whenCreate_thenRestaurantCreated() {
        Restaurant restaurant =
                restaurantFactory.create(
                        OWNER_ID,
                        RESTAURANT_NAME,
                        CAPACITY,
                        restaurantHours,
                        restaurantConfiguration);

        Assertions.assertThat(restaurant).isNotNull();
        Assertions.assertThat(restaurant.getOwnerId()).isEqualTo(OWNER_ID);
        Assertions.assertThat(restaurant.getName()).isEqualTo(RESTAURANT_NAME);
        Assertions.assertThat(restaurant.getCapacity()).isEqualTo(CAPACITY);
        Assertions.assertThat(restaurant.getHours()).isEqualTo(restaurantHours);
        Assertions.assertThat(restaurant.getConfiguration()).isEqualTo(restaurantConfiguration);
    }

    @Test
    void givenEmptyName_whenCreate_throwsIllegalArgumentException() {
        String emptyName = "";

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        restaurantFactory.create(
                                OWNER_ID,
                                emptyName,
                                CAPACITY,
                                restaurantHours,
                                restaurantConfiguration));
    }

    @Test
    void givenCapacityLessThan1_whenCreate_throwsIllegalArgumentException() {
        int belowOneCapacity = -2;

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        restaurantFactory.create(
                                OWNER_ID,
                                RESTAURANT_NAME,
                                belowOneCapacity,
                                restaurantHours,
                                restaurantConfiguration));
    }
}
