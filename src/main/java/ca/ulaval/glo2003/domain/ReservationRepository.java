package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReservationRepository {

    void add(Reservation reservation);

    Optional<Reservation> get(String reservationId);

    void delete(String reservationId);

    List<Reservation> searchReservations(String restaurantId, LocalDate date, String customerName);

    Map<LocalDateTime, Integer> searchAvailabilities(Restaurant restaurant, LocalDate date);
}
