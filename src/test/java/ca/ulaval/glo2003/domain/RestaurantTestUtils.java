package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import java.time.LocalTime;

public class RestaurantTestUtils {
    public static RestaurantHours createRestaurantHours(String open, String close) {
        return new RestaurantHours(LocalTime.parse(open), LocalTime.parse(close));
    }

    public static RestaurantHoursDto createRestaurantHoursDTO(String open, String close) {
        RestaurantHoursDto restaurantHoursDto = new RestaurantHoursDto();
        restaurantHoursDto.open = open;
        restaurantHoursDto.close = close;
        return restaurantHoursDto;
    }

    public static RestaurantConfiguration createRestaurantReservation(int duration) {
        return new RestaurantConfiguration(duration);
    }

    public static RestaurantConfigurationDto createRestaurantReservationsDTO(int duration) {
        RestaurantConfigurationDto restaurantConfigurationDto = new RestaurantConfigurationDto();
        restaurantConfigurationDto.duration = duration;
        return restaurantConfigurationDto;
    }
}
