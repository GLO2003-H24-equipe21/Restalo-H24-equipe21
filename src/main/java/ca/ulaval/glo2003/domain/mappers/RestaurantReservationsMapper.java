package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dtos.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;

public class RestaurantReservationsMapper {

    public RestaurantReservations fromDto(RestaurantReservationsDto dto) {
        return new RestaurantReservations(dto.duration);
    }

    public RestaurantReservationsDto toDto(RestaurantReservations reservations) {
        RestaurantReservationsDto dto = new RestaurantReservationsDto();
        dto.duration = reservations.getDuration();
        return dto;
    }
}
