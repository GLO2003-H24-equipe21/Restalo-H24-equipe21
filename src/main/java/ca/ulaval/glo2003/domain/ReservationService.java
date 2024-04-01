package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
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
        Restaurant restaurant =
                restaurantRepository
                        .get(restaurantId)
                        .orElseThrow(() -> new NotFoundException("Restaurant does not exist"));
        Customer customer =
                customerFactory.create(
                        customerRequest.name, customerRequest.email, customerRequest.phoneNumber);
        Reservation reservation =
                reservationFactory.create(date, startTime, groupSize, customer, restaurant);

        reservationRepository.add(reservation);

        return reservation.getNumber();
    }

    public Reservation getReservation(String number) {
        return reservationRepository
                .get(number)
                .orElseThrow(() -> new NotFoundException("Reservation does not exist"));
    }

    // TODO
    public void deleteReservation(String number) {
        reservationRepository.delete(number);
    }

    // TODO
    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        Restaurant restaurant =
                restaurantRepository
                        .get(restaurantId)
                        .orElseThrow(() -> new NotFoundException("Restaurant does not exist"));

        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("Restaurant owner id is invalid");
        }

        List<Reservation> matchingReservations = new ArrayList<>();
        for (Reservation reservation : reservationRepository.searchReservations(restaurantId, ownerId, date, customerName)) {
            if (reservation.getRestaurant().getId().equals(restaurantId)) {
                matchingReservations.add(reservation);
            }
        }
        return matchingReservations;
    }
}
