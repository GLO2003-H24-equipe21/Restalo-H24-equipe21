package ca.ulaval.glo2003.domain.factories;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo2003.domain.entities.*;
import java.time.LocalTime;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationFactoryTest {

    private static final String DATE = "2024-03-01";
    private static final String START_TIME = "13:38:59";

    private static final String EARLY_START_TIME = "05:00:00";
    private static final String LATE_START_TIME = "16:30:00";

    private static final String INVALID_START_TIME = "16:30:00";

    private static final String INVALID_DATE = "01-2024-09";
    private static final Integer GROUP_SIZE = 4;

    private static final Restaurant RESTAURANT =
            new Restaurant(
                    UUID.randomUUID(),
                    "Rudy",
                    "Chez Rudy",
                    100,
                    new RestaurantHours(LocalTime.parse("07:00:00"), LocalTime.parse("17:00:00")),
                    new RestaurantConfiguration(60));
    @Mock Customer customer;

    ReservationFactory reservationFactory;

    ReservationTime reservationTime;

    @BeforeEach
    void setUp() {

        reservationFactory = new ReservationFactory();
        reservationTime = new ReservationTime(LocalTime.parse(START_TIME), 60);
    }

    @Test
    void givenValidInputs_thenReservationCreated() {
        Reservation reservation =
                reservationFactory.create(DATE, START_TIME, GROUP_SIZE, customer, RESTAURANT);

        Assertions.assertThat(reservation.getDate()).isEqualTo(DATE);
        Assertions.assertThat(reservation.getReservationTime()).isEqualTo(reservationTime);
        Assertions.assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
        Assertions.assertThat(reservation.getCustomer()).isEqualTo(customer);
        Assertions.assertThat(reservation.getRestaurant()).isEqualTo(RESTAURANT);
    }

    @Test
    void givenGroupSizeBelowOne_throwInvalidArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationFactory.create(DATE, START_TIME, 0, customer, RESTAURANT));
    }

    @Test
    void givenInvalidStartTime_throwInvalidArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        reservationFactory.create(
                                DATE, INVALID_START_TIME, GROUP_SIZE, customer, RESTAURANT));
    }

    @Test
    void givenStartTimeBeforeOpen_throwInvalidArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        reservationFactory.create(
                                DATE, EARLY_START_TIME, GROUP_SIZE, customer, RESTAURANT));
    }

    @Test
    void givenEndTimeAfterClose_throwInvalidArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        reservationFactory.create(
                                DATE, LATE_START_TIME, GROUP_SIZE, customer, RESTAURANT));
    }

    @Test
    void givenInvalidDate_throwInvalidArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        reservationFactory.create(
                                INVALID_DATE, START_TIME, GROUP_SIZE, customer, RESTAURANT));
    }
}
