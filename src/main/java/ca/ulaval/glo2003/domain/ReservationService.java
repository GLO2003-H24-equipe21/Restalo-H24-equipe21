package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.ReservationRepository;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.entities.Reservation;
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

    public ReservationDto getReservation(String number) {
        Reservation reservation = reservationRepository.get(number);

        return reservationMapper.toDto(reservation);
    }
}
