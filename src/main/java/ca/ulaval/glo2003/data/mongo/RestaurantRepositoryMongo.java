package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.data.mongo.entities.ReservationMongo;
import ca.ulaval.glo2003.data.mongo.entities.RestaurantMongo;
import ca.ulaval.glo2003.data.mongo.entities.RestaurantToReservationsMapMongo;
import ca.ulaval.glo2003.data.mongo.mappers.ReservationMongoMapper;
import ca.ulaval.glo2003.data.mongo.mappers.RestaurantMongoMapper;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.Search;
import dev.morphia.Datastore;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.morphia.query.filters.Filters.gt;

public class RestaurantRepositoryMongo implements RestaurantRepository {
    private final Datastore datastore;

    private final RestaurantMongoMapper restaurantMongoMapper = new RestaurantMongoMapper();

    public RestaurantRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Optional<Restaurant> get(String restaurantId) {
        return datastore.find(RestaurantMongo.class)
                .stream()
                .filter(restaurantMongo -> Objects.equals(restaurantMongo.id, restaurantId)).findFirst()
                .map(restaurantMongoMapper::fromMongo);
    }

    @Override
    public void add(Restaurant restaurant) {
        datastore.save(restaurantMongoMapper.toMongo(restaurant));
    }

    @Override
    public List<Restaurant> getByOwnerId(String ownerId) {

        return datastore.find(RestaurantMongo.class)
                .stream()
                .filter(restaurantMongo -> Objects.equals(restaurantMongo.ownerId, ownerId))
                .map(restaurantMongoMapper::fromMongo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> searchRestaurants(Search search) {

        return datastore.find(RestaurantMongo.class).stream()
                .filter(restaurant -> matchesRestaurantName(restaurantMongoMapper.fromMongo(restaurant), search.getName()))
                .filter(
                        restaurant ->
                                matchesRestaurantOpenHour(
                                        restaurantMongoMapper.fromMongo(restaurant).getHours(), search.getSearchOpened().getFrom()))
                .filter(
                        restaurant ->
                                matchesRestaurantCloseHour(
                                        restaurantMongoMapper.fromMongo(restaurant).getHours(), search.getSearchOpened().getTo()))
                .map(restaurantMongoMapper::fromMongo)
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

    @Override
    public Optional<Restaurant> delete(String restaurantId, String ownerId) {
        Restaurant restaurant = datastore.find(RestaurantMongo.class)
                .stream()
                .filter(restaurantMongo -> Objects.equals(restaurantMongo.ownerId, ownerId))
                .filter(restaurantMongo -> Objects.equals(restaurantMongo.id, restaurantId)).findFirst()
                .map(restaurantMongoMapper::fromMongo).orElse(null);
        if (Objects.isNull(restaurant)) {
            return Optional.empty();
        }
        return Optional.of(restaurant);
    }
}
