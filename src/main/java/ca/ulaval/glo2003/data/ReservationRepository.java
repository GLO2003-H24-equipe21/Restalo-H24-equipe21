package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.domain.entities.Reservation;

import java.util.HashMap;
import java.util.Map;

public class ReservationRepository {
    private final Map<String, Reservation> reservationIdToReservation;

    public ReservationRepository() {
        reservationIdToReservation = new HashMap<>();
    }

    public Reservation get(String reservationId) {
        return null;
    }

    public void add(Reservation reservation) {

    }

    public void delete(String reservationId) {

    }
}
