package ca.ulaval.glo2003.api.mappers;

import ca.ulaval.glo2003.api.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;

public class ReservationResponseMapper {
    public ReservationResponse fromDto(ReservationDto reservationDto) {
        ReservationResponse reservationResponse = new ReservationResponse();

        reservationResponse.number = reservationDto.number;
        reservationResponse.date = reservationDto.date;
        reservationResponse.time = reservationDto.time;
        reservationResponse.groupSize = reservationDto.groupSize;
        reservationResponse.customer = reservationDto.customer;
        reservationResponse.restaurant = reservationDto.restaurant;
        reservationResponse.restaurant.ownerId = null;
        reservationResponse.restaurant.reservations = null;

        return reservationResponse;
    }
}