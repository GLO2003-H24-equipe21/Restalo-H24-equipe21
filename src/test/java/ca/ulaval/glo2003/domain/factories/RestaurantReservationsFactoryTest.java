package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantReservationsFactoryTest {
    RestaurantReservationsFactory restaurantReservationsFactory;

    @BeforeEach
    public void setUp() {
        restaurantReservationsFactory = new RestaurantReservationsFactory();
    }

    @Test
    public void givenNullDuration_whenCreate_thenDurationIs60() {
        RestaurantReservations restaurantReservations = restaurantReservationsFactory.create(null);

        assertThat(restaurantReservations.getDuration()).isEqualTo(60);
    }

    @Test
    public void givenNonNullDuration_whenCreate_thenSpecifiedDuration() {
        Integer duration = 90;

        RestaurantReservations reservations = restaurantReservationsFactory.create(duration);

        assertThat(reservations.getDuration()).isEqualTo(duration);
    }
}
