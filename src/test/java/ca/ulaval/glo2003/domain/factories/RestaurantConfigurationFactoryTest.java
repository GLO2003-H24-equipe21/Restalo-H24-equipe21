package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantConfigurationFactoryTest {
    RestaurantConfigurationFactory restaurantConfigurationFactory;

    @BeforeEach
    public void setUp() {
        restaurantConfigurationFactory = new RestaurantConfigurationFactory();
    }

    @Test
    public void givenNullDuration_whenCreate_thenDurationIs60() {
        RestaurantConfiguration restaurantConfiguration =
                restaurantConfigurationFactory.create(null);

        assertThat(restaurantConfiguration.getDuration()).isEqualTo(60);
    }

    @Test
    public void givenNonNullDuration_whenCreate_thenSpecifiedDuration() {
        Integer duration = 90;

        RestaurantConfiguration reservations = restaurantConfigurationFactory.create(duration);

        assertThat(reservations.getDuration()).isEqualTo(duration);
    }
}
