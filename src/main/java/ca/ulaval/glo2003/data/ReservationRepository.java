package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.domain.entities.Reservation;

import java.util.Map;

public class ReservationRepository {

    Map<String, Reservation> reservationNumberToReservation;
    Map<String, String> reservationNumberToRestaurantId;
}
