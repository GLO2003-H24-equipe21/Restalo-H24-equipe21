package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.mappers.RestaurantConfigurationMapper;
import ca.ulaval.glo2003.domain.mappers.RestaurantHoursMapper;
import java.time.LocalTime;

public class RestaurantResponseFixture {
    private String id;
    private String ownerId = "owner";
    private String name = "restaurant";
    private int capacity = 14;

    private RestaurantHoursMapper restaurantHoursMapper = new RestaurantHoursMapper();
    private RestaurantHoursDto hours =
            restaurantHoursMapper.toDto(
                    new RestaurantHours(LocalTime.parse("10:00:00"), LocalTime.parse("23:30:00")));

    private final RestaurantConfigurationMapper restaurantConfigurationMapper =
            new RestaurantConfigurationMapper();
    private RestaurantConfigurationDto reservations =
            restaurantConfigurationMapper.toDto(new RestaurantConfiguration(60));

    public RestaurantResponse create(String restaurantId) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.id = restaurantId;
        restaurantResponse.name = name;
        restaurantResponse.capacity = capacity;
        restaurantResponse.hours = hours;
        restaurantResponse.reservations = reservations;
        return restaurantResponse;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    public String getId() {
        return this.id;
    }
}
