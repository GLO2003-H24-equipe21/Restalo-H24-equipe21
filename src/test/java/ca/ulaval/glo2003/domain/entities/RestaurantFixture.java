package ca.ulaval.glo2003.domain.entities;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestaurantFixture {
    private String id = UUID.randomUUID().toString();
    private String ownerId = "owner";
    private String name = "restaurant";
    private Integer capacity = 14;
    private RestaurantHours hours =
            new RestaurantHours(LocalTime.parse("10:00:00"), LocalTime.parse("23:30:00"));
    private RestaurantConfiguration configuration = new RestaurantConfiguration(60);
    private Map<String, Integer> availabilities = new HashMap<>();

    public Restaurant create() {
        return new Restaurant(id, ownerId, name, capacity, hours, configuration, availabilities);
    }

    public RestaurantFixture withId(String id) {
        this.id = id;
        return this;
    }

    public RestaurantFixture withOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public RestaurantFixture withName(String name) {
        this.name = name;
        return this;
    }

    public RestaurantFixture withCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public RestaurantFixture withRestaurantHours(LocalTime open, LocalTime close) {
        this.hours = new RestaurantHours(open, close);
        return this;
    }

    public RestaurantFixture withRestaurantConfiguration(Integer duration) {
        this.configuration = new RestaurantConfiguration(duration);
        return this;
    }

    public RestaurantFixture withAvailabilities(Map<String, Integer> availabilities) {
        this.availabilities = availabilities;
        return this;
    }
}
