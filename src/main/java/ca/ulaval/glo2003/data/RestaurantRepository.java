package ca.ulaval.glo2003.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantRepository {

    Map<String, List<String>> ownerIdToRestaurantsId = new HashMap<>();
    Map<String, Restaurant> restaurantIdToRestaurant = new HashMap<>();
    Map<String, String> restaurantIdToOwnerId = new HashMap<>();
    
}
