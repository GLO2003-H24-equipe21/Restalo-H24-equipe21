package ca.ulaval.glo2003.domain.entities;

import net.bytebuddy.asm.Advice;

import java.time.LocalTime;

public class RestaurantFixture {
    private String ownerId = "owner";
    private String name = "restaurant";
    private Integer capacity = 14;
    private RestaurantHours hours =
            new RestaurantHours(LocalTime.of(10, 0, 0), LocalTime.of(23, 30, 0));
    private RestaurantReservations reservations = new RestaurantReservations(75);

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
