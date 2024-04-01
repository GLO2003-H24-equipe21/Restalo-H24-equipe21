package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import java.util.*;
import java.util.stream.Collectors;

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
    public Optional<Reservation> get(String reservationId) {
        return Optional.ofNullable(reservationIdToReservation.get(reservationId));
    }

    // TODO
    @Override
    public void delete(String reservationId) {}

    // TODO
    @Override
    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        return reservationIdToReservation.values().stream()
                .filter(customer -> matchesCustomerName(customer.getCustomer(), customerName))
                .filter(reservation -> matchesDate(reservation, date))
                .collect(Collectors.toList());
    }

    private boolean matchesCustomerName(Customer customer, String CustomerName) {
        if (Objects.isNull(CustomerName)) return true;
        return customer.getName()
                .toLowerCase()
                .replaceAll("\\s", "")
                .contains(CustomerName.toLowerCase().replaceAll("\\s", ""));
    }

    private boolean matchesDate(Reservation reservation, String date) {
        if (Objects.isNull(date)) return true;
        return reservation.getDate().toString().matches(date);
    }
}
