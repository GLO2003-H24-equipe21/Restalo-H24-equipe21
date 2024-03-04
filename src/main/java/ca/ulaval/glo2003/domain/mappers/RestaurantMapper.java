package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;

import java.time.format.DateTimeFormatter;

public class RestaurantMapper {

    public RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();

        dto.ownerId = restaurant.getOwnerId();
        dto.id = restaurant.getId();
        dto.name = restaurant.getName();
        dto.capacity = restaurant.getCapacity();
        dto.hours = new RestaurantHoursMapper().toDto(restaurant.getHours());
        dto.reservations = new RestaurantReservationsMapper().toDto(restaurant.getReservations());

        return dto;
    }
}
