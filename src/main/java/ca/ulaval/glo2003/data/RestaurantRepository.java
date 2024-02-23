package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.domain.entities.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantRepository {
    private final Map<String, Restaurant> restaurantIdToRestaurant;

    public RestaurantRepository() {
        restaurantIdToRestaurant = new HashMap<>();
    }

    public Restaurant get(String restaurantId) {
        return null;
    }

    public List<Restaurant> findByOwnerId(String ownerId) {
        return null;
    }

    public void add(Restaurant restaurant) {

    }

    public void delete(String restaurantId) {

    }
}
