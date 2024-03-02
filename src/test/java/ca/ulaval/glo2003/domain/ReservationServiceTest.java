package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.ReservationRepository;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.CustomerDto;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.entities.*;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.mappers.ReservationMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import java.util.Optional;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {


    private static final String DATE = "2024-03-01";

    private static final Integer GROUP_SIZE = 4;

    private static final Restaurant RESTAURANT = new Restaurant("Rudy",
            "Chez Rudy", 100, new RestaurantHours(LocalTime.parse("07:00:00"),
            LocalTime.parse("17:00:00")), new RestaurantReservations(60));
    private static final String START_TIME = "13:38:59";

    private static final Customer CUSTOMER = new Customer ("Buggy", "Buggy.Boo@Asetin.com", "1234567890");

    ReservationMapper reservationMapper = new ReservationMapper();
    ReservationService reservationService;

    CustomerDto customerDto = new CustomerDto();

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    ReservationFactory reservationFactory;

    RestaurantRepository restaurantRepository = new RestaurantRepository();;

    CustomerFactory customerFactory;

    String number;

    Reservation reservation;


    @BeforeEach
    void setUp() {
        reservation = new Reservation(LocalDate.parse(DATE), new ReservationTime(LocalTime.parse(START_TIME), 60), GROUP_SIZE, CUSTOMER, RESTAURANT);
        number = reservation.getNumber();

//        reservationFactory = new ReservationFactory();

        restaurantRepository.add(RESTAURANT);

        customerFactory = new CustomerFactory();

        customerDto.name = "Buggy";
        customerDto.email = "Buggy.Boo@Asetin.com";
        customerDto.phoneNumber = "1234567890";


        reservationService = new ReservationService(reservationRepository, restaurantRepository, reservationFactory, customerFactory);
    }

    @Test
    void givenValidInputs_thenReservationCreated() {
        when(reservationFactory.create(DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT)).thenReturn(reservation);
//        when(customerFactory.create(anyString(),anyString(), anyString())).thenReturn(customer);

        String reservationNumber = reservationService.createReservation(RESTAURANT.getId(), DATE, START_TIME, GROUP_SIZE, customerDto);

        Assertions.assertThat(reservationNumber).isEqualTo(number);
    }

    @Test
    void givenValidInputs_thenReservationIsSaved() {
        when(reservationFactory.create(DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT)).thenReturn(reservation);

        String reservationNumber = reservationService.createReservation(RESTAURANT.getId(),
                DATE, START_TIME, GROUP_SIZE, customerDto);

        verify(reservationRepository).add(reservation);;
    }

    @Test
    void givenExistingNumber_thenFindsReservation() {
        when(reservationRepository.get(number)).thenReturn(reservation);

        ReservationDto gottenReservation = reservationService.getReservation(number);

        Assertions.assertThat(gottenReservation.date).isEqualTo(reservationMapper.toDto(reservation).date);
        Assertions.assertThat(gottenReservation.time.start).isEqualTo(reservationMapper.toDto(reservation).time.start);
        Assertions.assertThat(gottenReservation.time.end).isEqualTo(reservationMapper.toDto(reservation).time.end);
        Assertions.assertThat(gottenReservation.groupSize).isEqualTo(reservationMapper.toDto(reservation).groupSize);
        Assertions.assertThat(gottenReservation.restaurant).isEqualTo(reservationMapper.toDto(reservation).restaurant);
        Assertions.assertThat(gottenReservation.number).isEqualTo(reservationMapper.toDto(reservation).number);
    }

    @Test
    void givenNonExistingNumber_thenThrowIllegalArgumentException() {
        when(reservationRepository.get("invalid_number")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> reservationService.getReservation("invalid_number"));
    }

}