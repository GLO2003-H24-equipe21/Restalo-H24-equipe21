package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.factories.RestaurantConfigurationFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantHoursFactory restaurantHoursFactory;
    private final RestaurantConfigurationFactory restaurantConfigurationFactory;
    private final RestaurantFactory restaurantFactory;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            RestaurantFactory restaurantFactory,
            RestaurantHoursFactory restaurantHoursFactory,
            RestaurantConfigurationFactory restaurantConfigurationFactory) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantFactory = restaurantFactory;
        this.restaurantHoursFactory = restaurantHoursFactory;
        this.restaurantConfigurationFactory = restaurantConfigurationFactory;
        restaurantMapper = new RestaurantMapper();
    }

    public String createRestaurant(
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHoursDto hoursDto,
            RestaurantConfigurationDto reservationsDto) {
        RestaurantHours hours = restaurantHoursFactory.create(hoursDto.open, hoursDto.close);
        RestaurantConfiguration reservations =
                restaurantConfigurationFactory.create(
                        Objects.requireNonNullElse(
                                        reservationsDto, new RestaurantConfigurationDto())
                                .duration);
        Restaurant restaurant =
                restaurantFactory.create(ownerId, name, capacity, hours, reservations);

        restaurantRepository.add(restaurant);

        return restaurant.getId();
    }

    public RestaurantDto getRestaurant(String restaurantId, String ownerId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);

        if (Objects.isNull(restaurant)) {
            throw new NotFoundException("Restaurant does not exist.");
        }
        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("Restaurant owner id is invalid");
        }

        return restaurantMapper.toDto(restaurant);
    }

    public List<RestaurantDto> listRestaurants(String ownerId) {
        List<Restaurant> restaurants = restaurantRepository.getByOwnerId(ownerId);

        return restaurants.stream().map(restaurantMapper::toDto).collect(Collectors.toList());
    }
}
