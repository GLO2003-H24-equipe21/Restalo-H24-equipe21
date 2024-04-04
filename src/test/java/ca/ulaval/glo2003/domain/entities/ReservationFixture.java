package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class ReservationFixture {
    private String number = "123456789123456789";
    private LocalDate date = LocalDate.now().plusDays(2);
    private ReservationTime reservationTime = new ReservationTime(LocalTime.parse("12:00:00"), 60);
    private Integer groupSize = 3;
    private Customer customer = new CustomerFixture().create();
    private String restaurantId = UUID.randomUUID().toString();

    public Reservation create() {
        return new Reservation(number, date, reservationTime, groupSize, customer, restaurantId);
    }
}
