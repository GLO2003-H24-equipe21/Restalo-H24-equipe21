package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final UUID id;
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
        this.id = UUID.randomUUID();
        this.date = date;
        this.reservationTime = reservationTime;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    public String getId() {
        return id.toString();
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
        return Objects.equals(id, that.id)
                && Objects.equals(date, that.date)
                && Objects.equals(reservationTime, that.reservationTime)
                && Objects.equals(groupSize, that.groupSize)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, reservationTime, groupSize, customer, restaurant);
    }
}
