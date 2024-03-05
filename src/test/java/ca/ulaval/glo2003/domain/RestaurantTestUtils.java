package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
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

    public static RestaurantReservations createRestaurantReservation(int duration) {
        return new RestaurantReservations(duration);
    }

    public static RestaurantReservationsDto createRestaurantReservationsDTO(int duration) {
        RestaurantReservationsDto restaurantReservationsDto = new RestaurantReservationsDto();
        restaurantReservationsDto.duration = duration;
        return restaurantReservationsDto;
    }
}
