package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import java.util.List;

public interface RestaurantRepository {

    Restaurant get(String restaurantId);

    void add(Restaurant restaurant);

    List<Restaurant> getByOwnerId(String ownerId);

    List<Restaurant> searchRestaurants(Search search);

    void delete(String restaurantId, String ownerId);
}
