package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantRepository {

    private final Map<String, Restaurant> restaurantIdToRestaurant;

    public RestaurantRepository() {
        restaurantIdToRestaurant = new HashMap<>();
    }

    public Restaurant get(String restaurantId) {
        if (!restaurantIdToRestaurant.containsKey(restaurantId)) {
            throw new IllegalArgumentException("EXCEPTION A CHANGER");
        }
        return restaurantIdToRestaurant.get(restaurantId);
    }

    public void add(Restaurant restaurant) {
        restaurantIdToRestaurant.put(restaurant.getId(), restaurant);
    }

    public List<Restaurant> getByOwnerId(String ownerId) {
        return restaurantIdToRestaurant.values().stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());
    }

    public List<Restaurant> searchRestaurants(Search search) {
        return restaurantIdToRestaurant.values().stream()
                .filter(restaurant -> matchesRestaurantName(restaurant, search.getName()))
                .filter(
                        restaurant ->
                                matchesRestaurantOpenHour(
                                        restaurant, search.getSearchOpened().getFrom()))
                .filter(
                        restaurant ->
                                matchesRestaurantCloseHour(
                                        restaurant, search.getSearchOpened().getTo()))
                .collect(Collectors.toList());
    }

    private boolean matchesRestaurantName(Restaurant restaurant, String name) {
        if (name.equals("*")) {
            return true;
        }
        return restaurant
                .getName()
                .toLowerCase()
                .replaceAll("\\s", "")
                .contains(name.toLowerCase().replaceAll("\\s", ""));
    }

    private boolean matchesRestaurantOpenHour(Restaurant restaurant, LocalTime from) {
        return restaurant.getHours().getOpen().isAfter(from);
    }

    private boolean matchesRestaurantCloseHour(Restaurant restaurant, LocalTime to) {
        return restaurant.getHours().getClose().isBefore(to);
    }
}
