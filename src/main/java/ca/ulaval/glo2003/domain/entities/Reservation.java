package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Objects;
import java.util.UUID;

public class Reservation {

    private String number;
    private LocalDate date;

    private ReservationTime reservationTime;
    private Integer groupSize;
    private final Customer customer;
    private final Restaurant restaurant;

    public Reservation(String date, String startTime, Integer groupSize, Customer customer, Restaurant restaurant) {
        setDate(date);
        setReservationTime(startTime);
        setGroupSize(groupSize);
        setNumber();
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public Reservation(LocalDate date, LocalTime startTime, Integer groupSize, Customer customer, Restaurant restaurant) {

        setDate(date.toString());
        setGroupSize(groupSize);
        setReservationTime(startTime.toString());
        setNumber();
        this.restaurant = restaurant;
        this.customer = customer;
    }

    private void setNumber() {
        this.number =  String.format("%040d", new java.math.BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    }

    public void setDate(String date) {
        if (date == null) throw new NullPointerException("Date must be provided");
        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not valid (yyyy-MM-dd)");
        }
    }

    public void setGroupSize(Integer groupSize) {
        if (groupSize == null) throw new NullPointerException("Group size must be provided");
        if (groupSize < 1) throw new IllegalArgumentException("Group size must be greater or equal to one");
        this.groupSize = groupSize;
    }

    public void setReservationTime(String startTimeString) {
        if (startTimeString == null) throw new NullPointerException("Start time must be provided");
        LocalTime startTime;
        try {
            startTime = LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Start time format is not valid (HH:mm:ss)");
        }

        int duration = this.restaurant.getReservations().getDuration();
        this.reservationTime = new ReservationTime(startTime, duration);

        LocalTime openTime = this.restaurant.getHours().getOpen();
        LocalTime closeTime = this.restaurant.getHours().getClose();

        if (openTime.isAfter(this.reservationTime.getStart())){
            throw new IllegalArgumentException("The reservation must start after the restaurant opens");
        }

        if (closeTime.isBefore(this.reservationTime.getEnd())){
            throw new IllegalArgumentException("The reservation must end before the restaurant closes");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return this.reservationTime;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {return customer;}
    public String getId() {return number;}

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
