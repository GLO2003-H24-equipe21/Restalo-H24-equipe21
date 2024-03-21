package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.mappers.RestaurantConfigurationMapper;
import ca.ulaval.glo2003.domain.mappers.RestaurantHoursMapper;
import java.time.LocalTime;

public class RestaurantRequestFixture {

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

    public CreateRestaurantRequest create() {
        CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
        restaurantRequest.name = name;
        restaurantRequest.capacity = capacity;
        restaurantRequest.hours = hours;
        restaurantRequest.reservations = reservations;
        return restaurantRequest;
    }

    public CreateRestaurantRequest withInvalidParameter(int invalidCapacity) {
        CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
        restaurantRequest.name = name;
        restaurantRequest.capacity = invalidCapacity;
        restaurantRequest.hours = hours;
        restaurantRequest.reservations = reservations;
        return restaurantRequest;
    }

    public CreateRestaurantRequest withMissingParameter() {
        CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
        restaurantRequest.capacity = capacity;
        restaurantRequest.hours = hours;
        restaurantRequest.reservations = reservations;
        return restaurantRequest;
    }

    public RestaurantRequestFixture withInvalidOwnerId(String InvalidOwnerId) {
        this.ownerId = InvalidOwnerId;
        return this;
    }

    public RestaurantRequestFixture withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantRequestFixture withRestaurantHours(LocalTime open, LocalTime close) {
        this.hours = restaurantHoursMapper.toDto(new RestaurantHours(open, close));
        return this;
    }

    public String getOwnerId() {
        return this.ownerId;
    }
}
