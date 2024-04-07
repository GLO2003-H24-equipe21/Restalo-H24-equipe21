package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class ReservationRepositoryTest {

    protected abstract ReservationRepository createRepository();

    private ReservationRepository reservationRepository;
    Reservation reservation;
    Reservation otherReservation;
    String reservationNumber;
    String number;
    LocalDate date;
    LocalTime time;
    LocalDateTime dateTime;
    Customer customer;
    String restaurantId;
    Integer restaurantDuration;

    @BeforeEach
    public void setUp() {
        number = "5";
        date = LocalDate.parse("2024-04-30");
        time = LocalTime.parse("12:00:00");
        dateTime = LocalDateTime.of(date, time);
        customer = new Customer("Rudy", "rudyasaal3@gmail.com", "5555555555");
        restaurantId = "restaurantId2";
        restaurantDuration = 60;

        reservationRepository = createRepository();

        reservation = new ReservationFixture().create();
        otherReservation = new ReservationFixture().create();
        reservationNumber = reservation.getNumber();
    }

    @Test
    public void givenNonEmptyRepository_whenGet_thenReturnsReservation() {
        reservationRepository.add(reservation);

        Optional<Reservation> gottenReservation = reservationRepository.get(reservationNumber);

        assertThat(gottenReservation.isPresent()).isTrue();
        assertThat(gottenReservation.get()).isEqualTo(reservation);
    }

    @Test
    public void givenEmptyRepository_whenGet_thenReturnsNull() {
        Optional<Reservation> gottenReservation = reservationRepository.get(reservationNumber);

        assertThat(gottenReservation.isEmpty()).isTrue();
    }

    @Test
    public void
            givenDefaultSearchForRestaurant_whenSearch_thenReturnsAllReservationsInRepository() {
        reservationRepository.add(reservation);

        Reservation reservation2 =
                new Reservation(
                        number,
                        date,
                        new ReservationTime(time, restaurantDuration),
                        3,
                        customer,
                        reservation.getRestaurantId());

        reservationRepository.add(reservation2);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(reservation.getRestaurantId(), null, null);

        assertThat(gottenReservations).containsExactly(reservation, reservation2);
    }

    @Test
    public void givenNoMatchSearch_whenSearch_thenReturnsEmptyList() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations("NoRestaurant", LocalDate.now(), "Kevin");

        assertThat(gottenReservations).isEqualTo(List.of());
    }

    @Test
    public void
            givenSavedReservations_whenSearchReservationWithId_shouldReturnReservationListWithThatId() {

        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(reservation.getRestaurantId(), null, null);

        Assertions.assertThat(gottenReservations).isEqualTo(List.of(reservation));
    }

    @Test
    public void
            givenSavedReservations_whenSearchReservationWithDate_shouldReturnReservationListWithThatDate() {

        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(
                        reservation.getRestaurantId(), reservation.getDate(), null);

        Assertions.assertThat(gottenReservations).isEqualTo(List.of(reservation));
    }

    @Test
    public void
            givenSavedReservations_whenSearchReservationWithCustomerName_shouldReturnReservationListWithThatCustomerName() {

        reservationRepository.add(reservation);

        customer = new Customer("Rudy Saal", "rudyasaal3@gmail.com", "5555555555");

        Reservation reservation2 =
                new Reservation(
                        number,
                        date,
                        new ReservationTime(time, restaurantDuration),
                        3,
                        customer,
                        restaurantId);

        reservationRepository.add(reservation2);

        List<Reservation> gottenReservationNormal =
                reservationRepository.searchReservations(restaurantId, null, "Rudy Saal");
        List<Reservation> gottenReservationLowerCase =
                reservationRepository.searchReservations(restaurantId, null, "rudy saal");
        List<Reservation> gottenReservationUpperCase =
                reservationRepository.searchReservations(restaurantId, null, "RUDY SAAL");
        List<Reservation> gottenReservationNoSpace =
                reservationRepository.searchReservations(restaurantId, null, "RudySaal");
        List<Reservation> gottenReservationExtraSpaces =
                reservationRepository.searchReservations(restaurantId, null, "Ru dy Sa al");

        Assertions.assertThat(gottenReservationNormal).isEqualTo(List.of(reservation2));
        Assertions.assertThat(gottenReservationLowerCase).isEqualTo(List.of(reservation2));
        Assertions.assertThat(gottenReservationUpperCase).isEqualTo(List.of(reservation2));
        Assertions.assertThat(gottenReservationNoSpace).isEqualTo(List.of(reservation2));
        Assertions.assertThat(gottenReservationExtraSpaces).isEqualTo(List.of(reservation2));
    }

    @Test
    public void
            givenSavedReservations_whenSearchReservationWithAllParameters_shouldReturnMatchingReservationList() {

        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(
                        reservation.getRestaurantId(),
                        reservation.getDate(),
                        reservation.getCustomer().getName());

        Assertions.assertThat(gottenReservations).isEqualTo(List.of(reservation));
    }

    @Test
    public void
            givenNoSavedReservation_whenSearchAvailabilities_shouldReturnAvailabilitiesMapWithoutCapacitiesLowered() {

        Restaurant restaurant =
                new Restaurant(
                        restaurantId,
                        "",
                        "",
                        10,
                        new RestaurantHours(time, time.plusHours(3)),
                        new RestaurantConfiguration(restaurantDuration));

        Map<LocalDateTime, Integer> availabilities =
                reservationRepository.searchAvailabilities(restaurant, date);

        Map<LocalDateTime, Integer> expectedAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(10)
                        .on(date)
                        .from(time)
                        .to(time.plusHours(2))
                        .create();

        Assertions.assertThat(availabilities).isEqualTo(expectedAvailabilities);
    }

    @Test
    public void
            givenSavedReservation_whenSearchAvailabilities_shouldReturnAvailabilitiesMapWithCapacitiesLowered() {

        reservationRepository.add(reservation);

        Restaurant restaurant =
                new Restaurant(
                        restaurantId,
                        "",
                        "",
                        10,
                        new RestaurantHours(time, time.plusHours(3)),
                        new RestaurantConfiguration(restaurantDuration));

        Reservation reservation2 =
                new Reservation(
                        number,
                        date,
                        new ReservationTime(time, restaurant.getConfiguration().getDuration()),
                        3,
                        customer,
                        restaurantId);

        reservationRepository.add(reservation2);

        Map<LocalDateTime, Integer> availabilities =
                reservationRepository.searchAvailabilities(restaurant, date);

        Map<LocalDateTime, Integer> expectedAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(7)
                        .on(date)
                        .from(time)
                        .to(time.plusHours(1))
                        .create();

        Map<LocalDateTime, Integer> expectedAvailabilitiesFull =
                new AvailabilitiesFixture()
                        .withCapacity(10)
                        .on(date)
                        .from(time.plusHours(1))
                        .to(time.plusHours(2))
                        .create();

        expectedAvailabilities.putAll(expectedAvailabilitiesFull);
        Assertions.assertThat(expectedAvailabilities).isEqualTo(availabilities);
    }
}
