package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;

import java.time.LocalTime;

public class RestaurantTestUtils {
    public static RestaurantHours createRestaurantHours(String open, String close) {
        return new RestaurantHours(
                LocalTime.parse(open),
                LocalTime.parse(close)
        );
    }

    public static RestaurantReservations createRestaurantReservation(int duration) {
        return new RestaurantReservations(
                duration
        );
    }

}
