package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;

public class RestaurantFactory {
    public Restaurant create(
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHours hours,
            RestaurantReservations reservations) {
        verifyNameNotEmpty(name);
        verifyCapacityAtLeastOne(capacity);

        return new Restaurant(ownerId, name, capacity, hours, reservations);
    }

    private void verifyNameNotEmpty(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Restaurant name must not be empty");
    }

    private void verifyCapacityAtLeastOne(Integer capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException("Restaurant minimal capacity must be one");
    }
}
