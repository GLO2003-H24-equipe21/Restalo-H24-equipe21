package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationFixture {
    private LocalDate date = LocalDate.now().plusDays(2);
    private ReservationTime reservationTime = new ReservationTime(LocalTime.parse("12:00:00"), 60);
    private Integer groupSize = 3;
    private Customer customer = new CustomerFixture().create();
    private Restaurant restaurant = new RestaurantFixture().create();

    public Reservation create() {
        return new Reservation(date, reservationTime, groupSize, customer, restaurant);
    }
}
