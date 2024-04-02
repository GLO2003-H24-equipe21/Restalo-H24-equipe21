package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class ReservationRepositoryInMemory implements ReservationRepository {

    private final Map<String, Reservation> reservationIdToReservation;
    private final Map<String, List<Reservation>> restaurantIdToReservations;

    public ReservationRepositoryInMemory() {
        reservationIdToReservation = new HashMap<>();
        restaurantIdToReservations = new HashMap<>();
    }

    @Override
    public void add(Reservation reservation) {
        reservationIdToReservation.put(reservation.getNumber(), reservation);

        List<Reservation> reservations = restaurantIdToReservations.getOrDefault(reservation.getRestaurantId(), new ArrayList<>());
        reservations.add(reservation);
        restaurantIdToReservations.put(reservation.getRestaurantId(), reservations);
    }

    @Override
    public Optional<Reservation> get(String reservationId) {
        return Optional.ofNullable(reservationIdToReservation.get(reservationId));
    }

    // TODO
    @Override
    public void delete(String reservationId) {}

    @Override
    public List<Reservation> searchReservations(
            String restaurantId, LocalDate date, String customerName) {
        return restaurantIdToReservations.getOrDefault(restaurantId, new ArrayList<>()).stream()
                .filter(reservation -> matchesCustomerName(reservation.getCustomer(), customerName))
                .filter(reservation -> matchesDate(reservation.getDate(), date))
                .collect(Collectors.toList());
    }

    private boolean matchesCustomerName(Customer customer, String customerName) {
        if (Objects.isNull(customerName)) return true;
        return customer.getName()
                .toLowerCase()
                .replaceAll("\\s", "")
                .contains(customerName.toLowerCase().replaceAll("\\s", ""));
    }

    private boolean matchesDate(LocalDate reservationDate, LocalDate date) {
        if (Objects.isNull(date)) return true;
        return reservationDate.equals(date);
    }

    @Override
    public Map<LocalDateTime, Integer> searchAvailabilities(Restaurant restaurant, LocalDate date) {
        List<Reservation> reservations = searchReservations(
                restaurant.getId(), date, null
        );
        List<LocalDateTime> intervals =
                create15MinutesIntervals(
                        date,
                        roundToNext15Minutes(restaurant.getHours().getOpen()),
                        roundToNext15Minutes(restaurant.getHours().getClose().plusSeconds(1).minusMinutes(restaurant.getConfiguration().getDuration()))
                );
        Map<LocalDateTime, Integer> availabilities = new LinkedHashMap<>();

        intervals.forEach(dateTime -> availabilities.put(dateTime, restaurant.getCapacity()));
        for (Reservation reservation: reservations) {
            List<LocalDateTime> reservationInterval =
                    create15MinutesIntervals(
                            reservation.getDate(),
                            reservation.getReservationTime().getStart(),
                            reservation.getReservationTime().getEnd()
                    );
            reservationInterval.forEach(dateTime -> availabilities.put(dateTime, availabilities.get(dateTime) - reservation.getGroupSize()));
        }

        return availabilities;
    }

    private List<LocalDateTime> create15MinutesIntervals(LocalDate date, LocalTime start, LocalTime end) {
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        for (LocalTime current = start; current.isBefore(end); current = current.plusMinutes(15)) {
            localDateTimes.add(LocalDateTime.of(date, current));
        }
        return localDateTimes;
    }

    private LocalTime roundToNext15Minutes(LocalTime time) {
        if (time.getNano() != 0) {
            time = time.plusSeconds(1);
        }
        return time.withNano(0).plusSeconds((4500 - (time.toSecondOfDay() % 3600)) % 900);
    }
}
