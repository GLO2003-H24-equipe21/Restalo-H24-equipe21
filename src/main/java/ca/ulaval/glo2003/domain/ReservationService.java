package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.ReservationRepository;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.CustomerDto;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import ca.ulaval.glo2003.domain.mappers.ReservationMapper;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerFactory customerFactory;
    private final ReservationMapper reservationMapper;

    public ReservationService(
            ReservationRepository reservationRepository,
            RestaurantRepository restaurantRepository,
            ReservationFactory reservationFactory,
            CustomerFactory customerFactory) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.customerFactory = customerFactory;
        this.reservationMapper = new ReservationMapper();
    }

    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            CustomerDto customerDto) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        Customer customer =
                customerFactory.create(
                        customerDto.name, customerDto.email, customerDto.phoneNumber);
        Reservation reservation =
                reservationFactory.create(date, startTime, groupSize, customer, restaurant);

        reservationRepository.add(reservation);

        return reservation.getId();
    }

    public ReservationDto getReservation(String number) {
        Reservation reservation = reservationRepository.get(number);

        return reservationMapper.toDto(reservation);
    }
}
