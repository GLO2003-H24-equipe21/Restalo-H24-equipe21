package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo2003.domain.entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class ReservationRepositoryTest {

    protected abstract ReservationRepository createRepository();

    private ReservationRepository reservationRepository;

    Reservation reservation;
    Reservation otherReservation;

    @BeforeEach
    public void setUp() {
        reservationRepository = createRepository();

        reservation =
                new ReservationFixture()
                        .withNumber(NUMBER)
                        .withCustomer(CUSTOMER)
                        .withGroupSize(GROUP_SIZE)
                        .withRestaurantId(RESTAURANT_ID)
                        .withDate(DATE)
                        .withReservationTime(TIME, DURATION)
                        .create();

        otherReservation =
                new ReservationFixture()
                        .withNumber(OTHER_NUMBER)
                        .withCustomer(OTHER_CUSTOMER)
                        .withGroupSize(OTHER_GROUP_SIZE)
                        .withRestaurantId(RESTAURANT_ID)
                        .withDate(DATE)
                        .withReservationTime(OTHER_TIME, DURATION)
                        .create();
    }

    @Test
    public void whenGet_thenReturnsReservation() {
        reservationRepository.add(reservation);

        Optional<Reservation> gottenReservation = reservationRepository.get(NUMBER);

        assertThat(gottenReservation.isPresent()).isTrue();
        assertThat(gottenReservation.get()).isEqualTo(reservation);
    }

    @Test
    public void givenNoReservationsAdded_whenGet_thenReturnsEmpty() {
        Optional<Reservation> gottenReservation = reservationRepository.get(NUMBER);

        assertThat(gottenReservation.isEmpty()).isTrue();
    }

    @Test
    public void whenDelete_thenReturnsDeletedReservation() {
        reservationRepository.add(reservation);

        Optional<Reservation> gottenReservation = reservationRepository.delete(NUMBER);

        assertThat(reservationRepository.get(NUMBER).isPresent()).isFalse();
        assertThat(gottenReservation.isPresent()).isTrue();
        assertThat(gottenReservation.get()).isEqualTo(reservation);
    }

    @Test
    public void givenNoReservationsAdded_whenDelete_thenReturnsEmpty() {
        Optional<Reservation> gottenReservation = reservationRepository.delete(NUMBER);

        assertThat(gottenReservation.isEmpty()).isTrue();
    }

    @Test
    public void whenDeleteAll_thenReturnsAllDeletedReservations() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations = reservationRepository.deleteAll(RESTAURANT_ID);

        assertThat(reservationRepository.get(NUMBER).isPresent()).isFalse();
        assertThat(reservationRepository.get(OTHER_NUMBER).isPresent()).isFalse();
        assertThat(gottenReservations).containsExactly(reservation, otherReservation);
    }

    @Test
    public void givenNoReservationsAdded_whenDeleteAll_thenReturnsEmptyList() {
        List<Reservation> gottenReservations = reservationRepository.deleteAll(RESTAURANT_ID);

        assertThat(gottenReservations).isEmpty();
    }

    @Test
    public void whenSearchReservationsByRestaurantId_thenReturnsMatchingReservations() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(RESTAURANT_ID, null, null);

        assertThat(gottenReservations).containsExactly(reservation, otherReservation);
    }

    @Test
    public void whenSearchReservationsByDate_thenReturnsMatchingReservations() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(RESTAURANT_ID, DATE, null);

        assertThat(gottenReservations).containsExactly(reservation, otherReservation);
    }

    @Test
    public void whenSearchReservationsByFullCustomerName_thenReturnsMatchingReservations() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(RESTAURANT_ID, null, CUSTOMER.getName());

        assertThat(gottenReservations).containsExactly(reservation);
    }

    @Test
    public void whenSearchReservationsByStartOfCustomerName_thenReturnsMatchingReservations() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);
        String startOfCustomerName = CUSTOMER.getName().substring(0, 3);

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(RESTAURANT_ID, null, startOfCustomerName);

        assertThat(gottenReservations).containsExactly(reservation);
    }

    @Test
    public void whenSearchReservationsBySpacedCustomerName_thenReturnsMatchingReservations() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);
        String spacedCustomerName = CUSTOMER.getName().replace("", " ");

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(RESTAURANT_ID, null, spacedCustomerName);

        assertThat(gottenReservations).containsExactly(reservation);
    }

    @Test
    public void whenSearchReservationsHasNoMatch_thenReturnsEmptyList() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);
        String noMatchCustomerName = "Kevin";

        List<Reservation> gottenReservations =
                reservationRepository.searchReservations(RESTAURANT_ID, DATE, noMatchCustomerName);

        assertThat(gottenReservations).isEmpty();
    }

    @Test
    public void
            givenNoReservationsAdded_whenSearchAvailabilities_thenReturnsDefaultRestaurantAvailabilities() {
        Map<LocalDateTime, Integer> expectedAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(RESTAURANT.getCapacity())
                        .on(DATE)
                        .from(OPEN)
                        .to(CLOSE.minusMinutes(DURATION))
                        .create();

        Map<LocalDateTime, Integer> gottenAvailabilities =
                reservationRepository.searchAvailabilities(RESTAURANT, DATE);

        assertThat(gottenAvailabilities).isEqualTo(expectedAvailabilities);
    }

    @Test
    public void
            givenOneReservation_whenSearchAvailabilities_thenReturnsCorrespondingRestaurantAvailabilities() {
        reservationRepository.add(reservation);
        Map<LocalDateTime, Integer> expectedAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(RESTAURANT.getCapacity())
                        .on(DATE)
                        .from(OPEN)
                        .to(CLOSE.minusMinutes(DURATION))
                        .create();
        Map<LocalDateTime, Integer> reservationAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(RESTAURANT.getCapacity() - reservation.getGroupSize())
                        .on(DATE)
                        .from(TIME.minusMinutes(DURATION - 15))
                        .to(TIME.plusMinutes(DURATION - 15))
                        .create();
        expectedAvailabilities.putAll(reservationAvailabilities);

        Map<LocalDateTime, Integer> gottenAvailabilities =
                reservationRepository.searchAvailabilities(RESTAURANT, DATE);

        assertThat(gottenAvailabilities).isEqualTo(expectedAvailabilities);
    }

    @Test
    public void
            givenOverlappingReservations_whenSearchAvailabilities_thenReturnsCorrespondingRestaurantAvailabilities() {
        reservationRepository.add(reservation);
        reservationRepository.add(otherReservation);
        Map<LocalDateTime, Integer> expectedAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(RESTAURANT.getCapacity())
                        .on(DATE)
                        .from(OPEN)
                        .to(CLOSE.minusMinutes(DURATION))
                        .create();
        Map<LocalDateTime, Integer> reservationAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(RESTAURANT.getCapacity() - otherReservation.getGroupSize())
                        .on(DATE)
                        .from(OTHER_TIME.minusMinutes(DURATION - 15))
                        .to(OTHER_TIME.plusMinutes(30))
                        .create();
        Map<LocalDateTime, Integer> overlap =
                new AvailabilitiesFixture()
                        .withCapacity(0)
                        .on(DATE)
                        .from(OTHER_TIME.minusMinutes(15))
                        .to(TIME.plusMinutes(15))
                        .create();
        Map<LocalDateTime, Integer> otherReservationAvailabilities =
                new AvailabilitiesFixture()
                        .withCapacity(RESTAURANT.getCapacity() - reservation.getGroupSize())
                        .on(DATE)
                        .from(TIME.plusMinutes(30))
                        .to(TIME.plusMinutes(DURATION - 15))
                        .create();
        expectedAvailabilities.putAll(reservationAvailabilities);
        expectedAvailabilities.putAll(overlap);
        expectedAvailabilities.putAll(otherReservationAvailabilities);

        Map<LocalDateTime, Integer> gottenAvailabilities =
                reservationRepository.searchAvailabilities(RESTAURANT, DATE);

        assertThat(gottenAvailabilities).isEqualTo(expectedAvailabilities);
    }

    private static final String NUMBER = "12345";
    private static final Integer GROUP_SIZE = 2;
    private static final Customer CUSTOMER = new CustomerFixture().withName("Edwin").create();
    private static final LocalTime TIME = LocalTime.parse("09:45:00");

    private static final String OTHER_NUMBER = "234567";
    private static final Integer OTHER_GROUP_SIZE = 3;
    private static final Customer OTHER_CUSTOMER =
            new CustomerFixture().withName("Rudy Saal").create();
    private static final LocalTime OTHER_TIME = LocalTime.parse("09:15:00");

    private static final String RESTAURANT_ID = "Restaurant id";
    private static final Integer DURATION = 60;
    private static final LocalTime OPEN = LocalTime.parse("08:00:00");
    private static final LocalTime CLOSE = LocalTime.parse("12:00:00");
    private static final Integer CAPACITY = 5;
    private static final LocalDate DATE = LocalDate.parse("2024-05-01");
    private static final Restaurant RESTAURANT =
            new RestaurantFixture()
                    .withId(RESTAURANT_ID)
                    .withRestaurantHours(OPEN, CLOSE)
                    .withCapacity(CAPACITY)
                    .withRestaurantConfiguration(DURATION)
                    .create();
}
