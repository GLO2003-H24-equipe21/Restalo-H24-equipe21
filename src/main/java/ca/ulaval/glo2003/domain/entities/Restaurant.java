package ca.ulaval.glo2003.domain.entities;

import java.util.UUID;

public class Restaurant {
    private final String ownerId;
    private final String id;
    private final String name;
    private final Integer capacity;
    private final RestaurantHours hours;
    private final RestaurantReservations reservations;

    public Restaurant(
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHours hours,
            RestaurantReservations reservations) {
        this.ownerId = ownerId;
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservations = reservations;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public RestaurantHours getHours() {
        return hours;
    }

    public String getId() {
        return id;
    }

    public RestaurantReservations getReservations() {
        return reservations;
    }
}
