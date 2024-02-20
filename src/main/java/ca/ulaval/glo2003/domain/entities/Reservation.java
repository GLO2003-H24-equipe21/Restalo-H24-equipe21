package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final UUID number;
    private final LocalDate date;
    private final ReservationTime reservationTime;
    private final Integer groupSize;
    private final Customer customer;
    private final Restaurant restaurant;

    public Reservation(LocalDate date, LocalTime startTime, Integer groupSize, Customer customer, Restaurant restaurant) {
        validateGroupSize(groupSize);
        validateStartTime(startTime, restaurant);
        this.number = UUID.randomUUID();
        this.date = date;
        this.reservationTime = new ReservationTime(startTime, restaurant.getReservations().getDuration());
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurant = restaurant;
    }

    private void validateGroupSize(Integer groupSize) {
        if (groupSize < 1) {
            throw new IllegalArgumentException("Reservation group size must be at least one");
        }
    }

    private void validateStartTime(LocalTime startTime, Restaurant restaurant) {
        ReservationTime tempReservationTime = new ReservationTime(startTime, restaurant.getReservations().getDuration());
        if (tempReservationTime.getEnd().isAfter(restaurant.getHours().getClose())) {
            throw new IllegalArgumentException(String.format("Reservation start time exceeds restaurant closing time (%s)", restaurant.getHours().getClose()));
        }
    }

    public UUID getNumber() {
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
