package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantHoursFactoryTest {
    private static final String VALID_OPEN_HOUR = "10:00:00";
    private static final String VALID_CLOSE_HOUR = "21:00:00";
    private static final String CLOSE_HOUR_TO_SOON = "10:30:00";

    private static final String INVALID_OPEN_HOUR = "invalid open";
    private static final String INVALID_CLOSE_HOUR = "invalid close";

    RestaurantHoursFactory restaurantHoursFactory;

    @BeforeEach
    public void setUp() {
        restaurantHoursFactory = new RestaurantHoursFactory();
    }

    @Test
    public void givenValidStringInputs_whenCreate_thenRestaurantHoursCreated() {
        RestaurantHours restaurantHours =
                restaurantHoursFactory.create(VALID_OPEN_HOUR, VALID_CLOSE_HOUR);

        assertThat(restaurantHours.getOpen()).isEqualTo(LocalTime.parse(VALID_OPEN_HOUR));
        assertThat(restaurantHours.getClose()).isEqualTo(LocalTime.parse(VALID_CLOSE_HOUR));
    }

    @Test
    public void givenValidLocalTimeInputs_whenCreate_thenRestaurantHoursCreated() {
        RestaurantHours restaurantHours =
                restaurantHoursFactory.create(
                        LocalTime.parse(VALID_OPEN_HOUR), LocalTime.parse(VALID_CLOSE_HOUR));

        assertThat(restaurantHours.getOpen()).isEqualTo(LocalTime.parse(VALID_OPEN_HOUR));
        assertThat(restaurantHours.getClose()).isEqualTo(LocalTime.parse(VALID_CLOSE_HOUR));
    }

    @Test
    public void givenInvalidOpenFormat_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> restaurantHoursFactory.create(INVALID_OPEN_HOUR, VALID_CLOSE_HOUR));
    }

    @Test
    public void givenInvalidCloseFormat_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> restaurantHoursFactory.create(VALID_OPEN_HOUR, INVALID_CLOSE_HOUR));
    }

    @Test
    public void givenCloseBeforeOpen_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> restaurantHoursFactory.create(VALID_CLOSE_HOUR, VALID_OPEN_HOUR));
    }

    @Test
    public void givenOpeningTimeLessThan1_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> restaurantHoursFactory.create(VALID_OPEN_HOUR, CLOSE_HOUR_TO_SOON));
    }
}
