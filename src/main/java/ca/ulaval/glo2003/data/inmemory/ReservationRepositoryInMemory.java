package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.entities.Reservation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepositoryInMemory implements ReservationRepository {

    private final Map<String, Reservation> reservationIdToReservation;

    public ReservationRepositoryInMemory() {
        reservationIdToReservation = new HashMap<>();
    }

    @Override
    public void add(Reservation reservation) {
        reservationIdToReservation.put(reservation.getNumber(), reservation);
    }

    @Override
    public Reservation get(String reservationId) {
        return reservationIdToReservation.get(reservationId);
    }

    // TODO
    @Override
    public void delete(String reservationId) {}

    // TODO
    @Override
    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        return null;
    }
}
