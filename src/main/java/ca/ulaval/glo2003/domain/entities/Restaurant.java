package ca.ulaval.glo2003.domain.entities;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public class Restaurant {

    private final UUID id;
    private final String name;
    private final Integer capacity;
    private final RestaurantHours hours;
    private final RestaurantReservations reservations;

    public Restaurant(String name, Integer capacity, RestaurantHours hours, RestaurantReservations reservations) {
        validateName(name);
        validateCapacity(capacity);
        this.id = UUID.randomUUID();
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservations = reservations;
    }

    private void validateName(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Name must not be empty");
    }

    private void validateCapacity(Integer capacity) {
        if (capacity < 1) throw new IllegalArgumentException("Minimal capacity must be one");
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
        return id.toString();
    }

    public RestaurantReservations getReservations() {
        return reservations;
    }
}
