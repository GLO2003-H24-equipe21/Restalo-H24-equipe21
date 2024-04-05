package ca.ulaval.glo2003.data.mongo;

import static dev.morphia.query.filters.Filters.gt;

import ca.ulaval.glo2003.data.mongo.entities.ReservationMongo;
import ca.ulaval.glo2003.data.mongo.entities.RestaurantToReservationsMapMongo;
import ca.ulaval.glo2003.data.mongo.mappers.ReservationMongoMapper;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import dev.morphia.Datastore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ReservationRepositoryMongo implements ReservationRepository {
    private final Datastore datastore;

    private final ReservationMongoMapper reservationMongoMapper = new ReservationMongoMapper();

    public ReservationRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void add(Reservation reservation) {
        addToReservationMap(reservation.getRestaurantId(), reservation);
        datastore.save(reservationMongoMapper.toMongo(reservation));
    }

    private Optional<RestaurantToReservationsMapMongo> getMap(String restaurantId) {
        return datastore.find(RestaurantToReservationsMapMongo.class).stream()
                .filter(
                        reservationMongo ->
                                Objects.equals(reservationMongo.restaurantId, restaurantId))
                .findFirst();
    }

    @Override
    public Optional<Reservation> get(String reservationId) {
        return datastore.find(ReservationMongo.class).stream()
                .filter(reservationMongo -> Objects.equals(reservationMongo.number, reservationId))
                .findFirst()
                .map(reservationMongoMapper::fromMongo);
    }

    @Override
    public Optional<Reservation> delete(String reservationId) {
        Reservation reservation = get(reservationId).orElse(null);
        if (Objects.isNull(reservation)) {
            return Optional.empty();
        }
        datastore.find(ReservationMongo.class).filter(gt("number", reservationId)).delete();
        RestaurantToReservationsMapMongo map =
                deleteFromReservationMap(reservation.getRestaurantId(), reservation).orElse(null);
        return Optional.of(reservation);
    }

    private void addToReservationMap(String restaurantId, Reservation reservation) {
        RestaurantToReservationsMapMongo reservationsMap = getMap(restaurantId).orElse(null);
        if (reservationsMap == null) {
            reservationsMap = new RestaurantToReservationsMapMongo(restaurantId);
        }
        reservationsMap.add(reservationMongoMapper.toMongo(reservation));
        modifyReservationsMap(restaurantId, reservationsMap);
    }

    private Optional<RestaurantToReservationsMapMongo> deleteFromReservationMap(
            String restaurantId, Reservation reservation) {
        RestaurantToReservationsMapMongo map = getMap(restaurantId).orElse(null);

        if (map == null) {
            return Optional.empty();
        }
        Boolean removed = map.remove(reservationMongoMapper.toMongo(reservation));
        if (!removed) {
            return Optional.empty();
        }
        modifyReservationsMap(restaurantId, map);
        return Optional.of(map);
    }

    private void modifyReservationsMap(String restaurantId, RestaurantToReservationsMapMongo map) {
        datastore.find(RestaurantToReservationsMapMongo.class).stream()
                .filter(
                        reservationMongo ->
                                Objects.equals(reservationMongo.restaurantId, restaurantId))
                .findFirst()
                .ifPresent(
                        oldMap ->
                                datastore
                                        .find(RestaurantToReservationsMapMongo.class)
                                        .filter(gt("restaurantId", restaurantId))
                                        .delete());
        datastore.save(map);
    }

    @Override
    public List<Reservation> deleteAll(String restaurantId) {
        List<Reservation> reservations = searchReservations(restaurantId, null, null);
        for (Reservation reservation : reservations) {
            delete(reservation.getNumber());
        }
        return reservations;
    }

    @Override
    public List<Reservation> searchReservations(
            String restaurantId, LocalDate date, String customerName) {

        RestaurantToReservationsMapMongo map =
                getMap(restaurantId).orElse(new RestaurantToReservationsMapMongo(restaurantId));
        return map.reservations.stream()
                .filter(
                        reservation ->
                                matchesCustomerName(
                                        reservationMongoMapper.fromMongo(reservation).getCustomer(),
                                        customerName))
                .filter(
                        reservation ->
                                matchesDate(
                                        reservationMongoMapper.fromMongo(reservation).getDate(),
                                        date))
                .map(reservationMongoMapper::fromMongo)
                .toList();
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
        List<Reservation> reservations = searchReservations(restaurant.getId(), date, null);
        List<LocalDateTime> intervals =
                create15MinutesIntervals(
                        date,
                        roundToNext15Minutes(restaurant.getHours().getOpen()),
                        roundToNext15Minutes(
                                restaurant
                                        .getHours()
                                        .getClose()
                                        .plusSeconds(1)
                                        .minusMinutes(
                                                restaurant.getConfiguration().getDuration())));
        Map<LocalDateTime, Integer> availabilities = new LinkedHashMap<>();

        intervals.forEach(dateTime -> availabilities.put(dateTime, restaurant.getCapacity()));

        for (Reservation reservation : reservations) {
            List<LocalDateTime> reservationInterval =
                    create15MinutesIntervals(
                            reservation.getDate(),
                            reservation.getReservationTime().getStart(),
                            reservation.getReservationTime().getEnd());
            reservationInterval.forEach(
                    dateTime ->
                            availabilities.put(
                                    dateTime,
                                    availabilities.get(dateTime) - reservation.getGroupSize()));
        }

        return availabilities;
    }

    private List<LocalDateTime> create15MinutesIntervals(
            LocalDate date, LocalTime start, LocalTime end) {
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
