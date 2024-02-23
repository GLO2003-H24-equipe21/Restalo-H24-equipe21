package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService() {
        restaurantRepository = new RestaurantRepository();
        restaurantMapper = new RestaurantMapper();
    }

    public String createRestaurant(String ownerId, String name, Integer capacity, RestaurantHours hours, RestaurantReservations reservations) {
        Restaurant restaurant = RestaurantFactory.createRestaurant(
                ownerId, name, capacity, hours, reservations
        );

        restaurantRepository.add(restaurant);

        return restaurant.getId();
    }

    public RestaurantDto getRestaurant(String restaurantId, String ownerId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new IllegalArgumentException("EXCEPTION A CHANGER");
        }
        return restaurantMapper.toDto(restaurant);
    }

    public RestaurantDto getRestaurant(String restaurantId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        return restaurantMapper.toDto(restaurant);
    }

    public List<RestaurantDto> listRestaurants(String ownerId) {
        List<Restaurant> restaurants = restaurantRepository.getByOwnerId(ownerId);
        return restaurants.stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    public String createReservation(ReservationDto dto) {
        return null;
    }
}
