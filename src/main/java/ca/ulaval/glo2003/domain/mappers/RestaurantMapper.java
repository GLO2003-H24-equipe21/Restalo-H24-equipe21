package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import java.util.Objects;

public class RestaurantMapper {
    RestaurantReservationsMapper restaurantReservationsMapper = new RestaurantReservationsMapper();
    RestaurantHoursMapper restaurantHoursMapper = new RestaurantHoursMapper();

    public Restaurant fromDto(RestaurantDto dto) {
        Objects.requireNonNull(dto.name, "Name must be provided");
        Objects.requireNonNull(dto.capacity, "Capacity must be provided");
        Objects.requireNonNull(dto.hours, "Opening hours must be provided");
        RestaurantHours hours = new RestaurantHoursMapper().fromDto(dto.hours);
        RestaurantReservations reservations =
                new RestaurantReservationsMapper().fromDto(dto.reservations);
        return new Restaurant(dto.ownerId, dto.name, dto.capacity, hours, reservations);
    }

    public RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.ownerId = restaurant.getOwnerId();
        dto.id = restaurant.getId();
        dto.name = restaurant.getName();
        dto.capacity = restaurant.getCapacity();
        dto.hours = restaurantHoursMapper.toDto(restaurant.getHours());
        dto.reservations = restaurantReservationsMapper.toDto(restaurant.getReservations());
        return dto;
    }
}
