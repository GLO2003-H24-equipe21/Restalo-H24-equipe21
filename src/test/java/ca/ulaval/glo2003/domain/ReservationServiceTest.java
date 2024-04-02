package ca.ulaval.glo2003.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.data.inmemory.ReservationRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.domain.entities.*;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    private static final String NUMBER = "12345678912345678910";
    private static final String DATE = "2024-03-01";

    private static final Integer GROUP_SIZE = 4;

    private static final Restaurant RESTAURANT =
            new Restaurant(
                    UUID.randomUUID().toString(),
                    "Rudy",
                    "Chez Rudy",
                    100,
                    new RestaurantHours(LocalTime.parse("07:00:00"), LocalTime.parse("17:00:00")),
                    new RestaurantConfiguration(60));
    private static final String START_TIME = "13:38:59";

    private static final String INVALID_NUMBER = "invalid_number";

    private static final Customer CUSTOMER =
            new Customer("Buggy", "Buggy.Boo@Asetin.com", "1234567890");

    private static final Map<LocalDateTime, Integer> AVAILABILITIES = new AvailabilitiesFixture().on(LocalDate.parse(DATE)).create();

    ReservationService reservationService;

    CustomerPojo customerPojo = new CustomerPojo("Buggy", "Buggy.Boo@Asetin.com", "1234567890");

    @Mock ReservationRepositoryInMemory reservationRepository;

    @Mock ReservationFactory reservationFactory;

    RestaurantRepositoryInMemory restaurantRepository = new RestaurantRepositoryInMemory();

    CustomerFactory customerFactory;

    String number;

    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation =
                new Reservation(
                        NUMBER,
                        LocalDate.parse(DATE),
                        new ReservationTime(LocalTime.parse(START_TIME), 60),
                        GROUP_SIZE,
                        CUSTOMER,
                        RESTAURANT.getId());
        number = reservation.getNumber();

        restaurantRepository.add(RESTAURANT);

        customerFactory = new CustomerFactory();

        reservationService =
                new ReservationService(
                        reservationRepository,
                        restaurantRepository,
                        reservationFactory,
                        customerFactory);
    }

    @Test
    void givenValidInputs_thenReservationCreated() {
        when(reservationFactory.create(DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES))
                .thenReturn(reservation);
        when(reservationRepository.searchAvailabilities(RESTAURANT, LocalDate.parse(DATE))).thenReturn(AVAILABILITIES);

        String reservationNumber =
                reservationService.createReservation(
                        RESTAURANT.getId(), DATE, START_TIME, GROUP_SIZE, customerPojo);

        Assertions.assertThat(reservationNumber).isEqualTo(number);
    }

    @Test
    void givenValidInputs_thenReservationIsSaved() {
        when(reservationFactory.create(DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES))
                .thenReturn(reservation);
        when(reservationRepository.searchAvailabilities(RESTAURANT, LocalDate.parse(DATE))).thenReturn(AVAILABILITIES);

        reservationService.createReservation(
                RESTAURANT.getId(), DATE, START_TIME, GROUP_SIZE, customerPojo);

        verify(reservationRepository).add(reservation);
    }

    @Test
    void givenExistingNumber_thenFindsReservation() {
        when(reservationRepository.get(number)).thenReturn(Optional.ofNullable(reservation));

        Reservation gottenReservation = reservationService.getReservation(number).getFirst();

        Assertions.assertThat(gottenReservation).isEqualTo(reservation);
    }

    @Test
    void givenNonExistingNumber_thenThrowNotFoundException() {
        when(reservationRepository.get(INVALID_NUMBER)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class, () -> reservationService.getReservation(INVALID_NUMBER));
    }
}
