package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;
import ca.ulaval.glo2003.domain.entities.Availability;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.factories.RestaurantConfigurationFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantHoursFactory restaurantHoursFactory;
    private final RestaurantConfigurationFactory restaurantConfigurationFactory;
    private final RestaurantFactory restaurantFactory;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            RestaurantFactory restaurantFactory,
            RestaurantHoursFactory restaurantHoursFactory,
            RestaurantConfigurationFactory restaurantConfigurationFactory) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantFactory = restaurantFactory;
        this.restaurantHoursFactory = restaurantHoursFactory;
        this.restaurantConfigurationFactory = restaurantConfigurationFactory;
    }

    public String createRestaurant(
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHoursPojo hoursPojo,
            RestaurantConfigurationPojo configurationPojo) {
        RestaurantHours hours = restaurantHoursFactory.create(hoursPojo.open, hoursPojo.close);
        RestaurantConfiguration reservations =
                restaurantConfigurationFactory.create(
                        Objects.requireNonNullElse(
                                        configurationPojo, new RestaurantConfigurationPojo(null))
                                .duration);
        Restaurant restaurant =
                restaurantFactory.create(ownerId, name, capacity, hours, reservations);

        restaurantRepository.add(restaurant);

        return restaurant.getId();
    }

    public Restaurant getRestaurant(String restaurantId, String ownerId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);

        if (Objects.isNull(restaurant)) {
            throw new NotFoundException("Restaurant does not exist.");
        }
        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("Restaurant owner id is invalid");
        }

        return restaurant;
    }

    public List<Restaurant> listRestaurants(String ownerId) {
        return restaurantRepository.getByOwnerId(ownerId);
    }

    // TODO
    public void deleteRestaurant(String restaurantId, String ownerId) {
        restaurantRepository.delete(restaurantId, ownerId);
    }

    // TODO
    public List<Availability> searchAvailabilities(String restaurantId, String date) {
        return null;
    }
}
