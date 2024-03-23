package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.domain.entities.Reservation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepository {

    private final Map<String, Reservation> reservationIdToReservation;

    public ReservationRepository() {
        reservationIdToReservation = new HashMap<>();
    }

    public void add(Reservation reservation) {
        reservationIdToReservation.put(reservation.getNumber(), reservation);
    }

    public Reservation get(String reservationId) {
        return reservationIdToReservation.get(reservationId);
    }

    // TODO
    public void delete(String reservationId) {}

    // TODO
    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        return null;
    }
}
