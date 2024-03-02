package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ReservationFactoryTest {

    private static final String DATE = "2024-03-01";
    private static final String START_TIME = "13:38:59";
    private static final Integer GROUP_SIZE = 4;

    private static final Restaurant RESTAURANT = new Restaurant("Rudy",
            "Chez Rudy", 100, new RestaurantHours(LocalTime.parse("07:00:00"),
            LocalTime.parse("17:00:00")), new RestaurantReservations(60));
    @Mock
    Customer customer;


    ReservationFactory reservationFactory;

    ReservationTime reservationTime;


    @BeforeEach
    void setUp() {

        reservationFactory = new ReservationFactory();
        reservationTime = new ReservationTime(LocalTime.parse(START_TIME), 60);
    }

    @Test
    void givenValidInputs_thenReservationCreated() {
        Reservation reservation = reservationFactory.create(DATE, START_TIME, GROUP_SIZE, customer, RESTAURANT);


        Assertions.assertThat(reservation.getDate()).isEqualTo(DATE);
        Assertions.assertThat(reservation.getReservationTime()).isEqualTo(reservationTime);
        Assertions.assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
        Assertions.assertThat(reservation.getCustomer()).isEqualTo(customer);
        Assertions.assertThat(reservation.getRestaurant()).isEqualTo(RESTAURANT);
    }

    @Test
    void givenGroupSizeBelowOne_throwInvalidArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> reservationFactory.create(DATE, START_TIME, 0, customer, RESTAURANT));
    }

    @Test
    void givenEndTimeAfterClose_throwInvalidArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> reservationFactory.create(DATE,"16:30:00", GROUP_SIZE, customer, RESTAURANT));
    }

    @Test
    void givenInvalidDate_throwInvalidArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> reservationFactory.create("01-2024-09",START_TIME, GROUP_SIZE, customer, RESTAURANT));
    }
}