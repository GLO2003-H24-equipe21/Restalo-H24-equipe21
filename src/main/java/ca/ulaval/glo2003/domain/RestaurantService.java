package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.ReservationRepository;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.CustomerDto;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final ReservationRepository reservationRepository;

    public RestaurantService() {
        restaurantRepository = new RestaurantRepository();
        restaurantMapper = new RestaurantMapper();
        reservationRepository = new ReservationRepository();
    }

    public String createRestaurant(String ownerId, String name, Integer capacity, RestaurantHoursDto restaurantHoursDto, RestaurantReservationsDto restaurantReservationsDto) {
        Restaurant restaurant = RestaurantFactory.create(
                ownerId, name, capacity, restaurantHoursDto, restaurantReservationsDto
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
        List<Restaurant> restaurants = restaurantRepository.findByOwnerId(ownerId);

        return restaurants.stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    public String createReservation(String date, String startTime, Integer groupSize, CustomerDto customerDto, RestaurantDto restaurantDto) {
        Reservation reservation = ReservationFactory.create(
                date, startTime, groupSize, customerDto, restaurantDto
        );

        reservationRepository.add(reservation);

        return reservation.getId();
    }
}
