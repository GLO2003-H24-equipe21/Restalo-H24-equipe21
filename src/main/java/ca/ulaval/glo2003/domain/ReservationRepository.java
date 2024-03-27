package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.Reservation;

import java.util.List;

public interface ReservationRepository {

    void add(Reservation reservation);

    Reservation get(String reservationId);

    void delete(String reservationId);

    List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName);
}
