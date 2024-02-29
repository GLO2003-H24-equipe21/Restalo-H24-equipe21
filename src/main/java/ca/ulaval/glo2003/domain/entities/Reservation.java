package ca.ulaval.glo2003.domain.entities;

import ca.ulaval.glo2003.api.requests.SafeRestaurant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Objects;
import java.util.UUID;

public class Reservation {

    private String number;
    private LocalDate date;
    private ReservationTime time;
    private Integer groupSize;
    private final Customer customer;
    private final Restaurant restaurant;

    public Reservation(String date, String startTime, Integer groupSize, Customer customer, Restaurant restaurant) {
        this.restaurant = restaurant;
        this.customer = customer;
        setDate(date);
        setTime(startTime);
        setGroupSize(groupSize);
        setNumber();
        if (this.restaurant == null) {
            throw new NullPointerException("null restaurant");
        }
    }

    public Reservation(LocalDate date, LocalTime startTime, Integer groupSize, Customer customer, Restaurant restaurant) {

        setDate(date.toString());
        setGroupSize(groupSize);
        setTime(startTime.toString());
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

    public void setTime(String startTimeString) {
        if (startTimeString == null) throw new NullPointerException("Start time must be provided");
        LocalTime startTime;
        try {
            startTime = LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Start time format is not valid (HH:mm:ss)");
        }

        int duration = this.restaurant.getReservations().getDuration();
        this.time = new ReservationTime(startTime, duration);

        LocalTime openTime = this.restaurant.getHours().getOpen();
        LocalTime closeTime = this.restaurant.getHours().getClose();

        if (openTime.isAfter(this.time.getStart())){
            throw new IllegalArgumentException("The reservation must start after the restaurant opens");
        }

        if (closeTime.isBefore(this.time.getEnd())){
            throw new IllegalArgumentException("The reservation must end before the restaurant closes");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return this.time;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {return customer;}
    public String getNumber() {return number;}

    public SafeRestaurant getRestaurant() {return new SafeRestaurant(restaurant);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(number, that.number)
                && Objects.equals(date, that.date)
                && Objects.equals(time, that.time)
                && Objects.equals(groupSize, that.groupSize)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, time, groupSize, customer, restaurant);
    }
}
