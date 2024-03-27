package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import dev.morphia.Datastore;

import java.util.List;

public class RestaurantRepositoryMongo implements RestaurantRepository {
    private final Datastore datastore;

    public RestaurantRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Restaurant get(String restaurantId) {
        return null;
    }

    @Override
    public void add(Restaurant restaurant) {

    }

    @Override
    public List<Restaurant> getByOwnerId(String ownerId) {
        return null;
    }

    @Override
    public List<Restaurant> searchRestaurants(Search search) {
        return null;
    }

    @Override
    public void delete(String restaurantId, String ownerId) {

    }
}
