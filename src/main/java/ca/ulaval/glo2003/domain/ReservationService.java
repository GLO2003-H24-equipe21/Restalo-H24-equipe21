package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerFactory customerFactory;

    public ReservationService(
            ReservationRepository reservationRepository,
            RestaurantRepository restaurantRepository,
            ReservationFactory reservationFactory,
            CustomerFactory customerFactory) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.customerFactory = customerFactory;
    }

    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            CustomerPojo customerRequest) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        if (Objects.isNull(restaurant)) {
            throw new NotFoundException("Restaurant does not exist");
        }
        Customer customer =
                customerFactory.create(
                        customerRequest.name, customerRequest.email, customerRequest.phoneNumber);
        Reservation reservation =
                reservationFactory.create(date, startTime, groupSize, customer, restaurant);

        reservationRepository.add(reservation);

        return reservation.getNumber();
    }

    public Reservation getReservation(String number) {
        Reservation reservation = reservationRepository.get(number);

        if (Objects.isNull(reservation)) {
            throw new NotFoundException("Reservation does not exist");
        }

        return reservation;
    }

    // TODO
    public void deleteReservation(String number) {
        reservationRepository.delete(number);
    }

    // TODO
    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        return null;
    }
}
