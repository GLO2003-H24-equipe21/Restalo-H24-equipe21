package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import java.util.Objects;

public class RestaurantConfigurationFactory {
    public RestaurantConfiguration create(Integer duration) {
        if (Objects.isNull(duration)) return new RestaurantConfiguration(60);

        return new RestaurantConfiguration(duration);
    }
}
