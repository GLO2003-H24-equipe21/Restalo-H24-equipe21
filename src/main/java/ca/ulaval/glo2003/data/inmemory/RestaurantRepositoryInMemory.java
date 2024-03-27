package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.Search;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RestaurantRepositoryInMemory implements RestaurantRepository {

    private final Map<String, Restaurant> restaurantIdToRestaurant;

    public RestaurantRepositoryInMemory() {
        restaurantIdToRestaurant = new HashMap<>();
    }

    public Restaurant get(String restaurantId) {
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
                                        restaurant.getHours(), search.getSearchOpened().getFrom()))
                .filter(
                        restaurant ->
                                matchesRestaurantCloseHour(
                                        restaurant.getHours(), search.getSearchOpened().getTo()))
                .collect(Collectors.toList());
    }

    private boolean matchesRestaurantName(Restaurant restaurant, String name) {
        if (Objects.isNull(name)) return true;
        return restaurant
                .getName()
                .toLowerCase()
                .replaceAll("\\s", "")
                .contains(name.toLowerCase().replaceAll("\\s", ""));
    }

    private boolean matchesRestaurantOpenHour(RestaurantHours restaurantHours, LocalTime from) {
        if (Objects.isNull(from)) return true;
        return !from.isBefore(restaurantHours.getOpen())
                && from.isBefore(restaurantHours.getClose());
    }

    private boolean matchesRestaurantCloseHour(RestaurantHours restaurantHours, LocalTime to) {
        if (Objects.isNull(to)) return true;
        return !to.isAfter(restaurantHours.getClose()) && to.isAfter(restaurantHours.getOpen());
    }

    // TODO
    public void delete(String restaurantId, String ownerId) {}
}
