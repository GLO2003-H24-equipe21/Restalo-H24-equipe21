package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantReservationsFactory;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantHoursFactory restaurantHoursFactory;
    private final RestaurantReservationsFactory restaurantReservationsFactory;
    private final RestaurantFactory restaurantFactory;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            RestaurantFactory restaurantFactory,
            RestaurantHoursFactory restaurantHoursFactory,
            RestaurantReservationsFactory restaurantReservationsFactory) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantFactory = restaurantFactory;
        this.restaurantHoursFactory = restaurantHoursFactory;
        this.restaurantReservationsFactory = restaurantReservationsFactory;
        restaurantMapper = new RestaurantMapper();
    }

    public String createRestaurant(
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHoursDto hoursDto,
            RestaurantReservationsDto reservationsDto) {
        RestaurantHours hours = restaurantHoursFactory.create(hoursDto.open, hoursDto.close);
        RestaurantReservations reservations =
                restaurantReservationsFactory.create(
                        Objects.requireNonNullElse(reservationsDto, new RestaurantReservationsDto())
                                .duration);
        Restaurant restaurant =
                restaurantFactory.create(ownerId, name, capacity, hours, reservations);

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

    public List<RestaurantDto> listRestaurants(String ownerId) {
        List<Restaurant> restaurants = restaurantRepository.getByOwnerId(ownerId);
        return restaurants.stream().map(restaurantMapper::toDto).collect(Collectors.toList());
    }
}
