package ca.ulaval.glo2003.domain.entities;

import java.time.LocalTime;
import java.util.UUID;

public class RestaurantFixture {
    private UUID restaurantId = UUID.randomUUID();
    private String ownerId = "owner";
    private String name = "restaurant";
    private Integer capacity = 14;
    private RestaurantHours hours =
            new RestaurantHours(LocalTime.parse("10:00:00"), LocalTime.parse("23:30:00"));
    private RestaurantConfiguration reservations = new RestaurantConfiguration(60);

    public Restaurant create() {
        return new Restaurant(restaurantId, ownerId, name, capacity, hours, reservations);
    }

    public RestaurantFixture withOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public RestaurantFixture withInvalidOwnerId(String InvalidOwnerId) {
        this.ownerId = InvalidOwnerId;
        return this;
    }

    public RestaurantFixture withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantFixture withRestaurantHours(LocalTime open, LocalTime close) {
        this.hours = new RestaurantHours(open, close);
        return this;
    }
}
