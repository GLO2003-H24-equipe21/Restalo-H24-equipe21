package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.dto.CustomerDto;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import ca.ulaval.glo2003.domain.mappers.ReservationMapper;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(restaurant)) {
            throw new NotFoundException("Restaurant does not exist");
        }
        Customer customer =
                customerFactory.create(
                        customerDto.name, customerDto.email, customerDto.phoneNumber);
        Reservation reservation =
                reservationFactory.create(date, startTime, groupSize, customer, restaurant);

        reservationRepository.add(reservation);

        return reservation.getNumber();
    }

    public ReservationDto getReservation(String number) {
        Reservation reservation = reservationRepository.get(number);

        if (Objects.isNull(reservation)) {
            throw new NotFoundException("Reservation does not exist");
        }

        return reservationMapper.toDto(reservation);
    }

    // TODO
    public void deleteReservation(String number) {
        reservationRepository.delete(number);
    }

    // TODO
    public List<ReservationDto> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        return null;
    }
}
