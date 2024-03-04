package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final String number;
    private final LocalDate date;
    private final ReservationTime reservationTime;
    private final Integer groupSize;
    private final Customer customer;
    private final Restaurant restaurant;

    public Reservation(
            LocalDate date,
            ReservationTime reservationTime,
            Integer groupSize,
            Customer customer,
            Restaurant restaurant) {
        this.number = createNumber();
        this.date = date;
        this.reservationTime = reservationTime;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public String createNumber() {
        return String.format("%040d", new java.math.BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(number, that.number)
                && Objects.equals(date, that.date)
                && Objects.equals(reservationTime, that.reservationTime)
                && Objects.equals(groupSize, that.groupSize)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, reservationTime, groupSize, customer, restaurant);
    }
}
