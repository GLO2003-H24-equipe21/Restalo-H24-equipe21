package ca.ulaval.glo2003.domain.entities;

import java.time.LocalTime;

public class RestaurantFixture {
    private String ownerId = "owner";
    private String name = "restaurant";
    private Integer capacity = 14;
    private RestaurantHours hours = new RestaurantHours(LocalTime.parse("10:00:00"), LocalTime.parse("23:30:00"));
    private RestaurantReservations reservations = new RestaurantReservations(60);

    public Restaurant create() {
        return new Restaurant(
                ownerId,
                name,
                capacity,
                hours,
                reservations
        );
    }

    public RestaurantFixture withOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
