package ca.ulaval.glo2003.domain.entities;

import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RestaurantTestUtils {
    public static List<Restaurant> createRestaurants(int number) {
        return IntStream.range(0, number)
                .mapToObj(
                        i ->
                                new Restaurant(
                                        String.format("owner %d", i),
                                        String.format("restaurant %d", i),
                                        5,
                                        new RestaurantHours(
                                                LocalTime.of(10, 0, 0), LocalTime.of(22, 0, 0)),
                                        new RestaurantConfiguration(75)))
                .collect(Collectors.toList());
    }

    public static List<RestaurantDto> createRestaurantDtos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(
                        restaurant -> {
                            RestaurantDto restaurantDto = new RestaurantDto();
                            RestaurantHoursDto hoursDto = new RestaurantHoursDto();
                            hoursDto.open =
                                    restaurant
                                            .getHours()
                                            .getOpen()
                                            .format(DateTimeFormatter.ISO_LOCAL_TIME);
                            hoursDto.close =
                                    restaurant
                                            .getHours()
                                            .getClose()
                                            .format(DateTimeFormatter.ISO_LOCAL_TIME);
                            RestaurantConfigurationDto reservationsDto =
                                    new RestaurantConfigurationDto();
                            reservationsDto.duration = restaurant.getReservations().getDuration();

                            restaurantDto.id = restaurant.getId();
                            restaurantDto.ownerId = restaurant.getOwnerId();
                            restaurantDto.name = restaurant.getName();
                            restaurantDto.capacity = restaurant.getCapacity();
                            restaurantDto.hours = hoursDto;
                            restaurantDto.reservations = reservationsDto;

                            return restaurantDto;
                        })
                .collect(Collectors.toList());
    }
}
