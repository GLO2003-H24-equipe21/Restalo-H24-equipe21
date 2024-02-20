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
    private LocalTime startTime;
    private Integer groupSize;
    private final Customer customer;
    private final Restaurant restaurant;

    public Reservation(String date, String startTime, Integer groupSize, Customer customer, Restaurant restaurant) {
        setDate(date);
        setStartTime(startTime);
        setGroupSize(groupSize);
        setNumber();
        this.restaurant = restaurant;
        this.customer = customer;
    }

    public Reservation(LocalDate date, LocalTime startTime, Integer groupSize, Customer customer, Restaurant restaurant) {

        setDate(date.toString());
        setStartTime(startTime.toString());
        setGroupSize(groupSize);
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

    public void setStartTime(String startTime) {
        if (startTime == null) throw new NullPointerException("Start time must be provided");
        try {
            this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

            int seconds = this.startTime.getMinute() * 60 + this.startTime.getSecond();
            //convert to seconds
            long addedSeconds = ((4500 - seconds) % 900) / 60;
            //only adds
            this.startTime = this.startTime.withSecond(0)
                    .withNano(0)
                    .plusMinutes((long) Math.ceil(addedSeconds));

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Start time format is not valid (HH:mm:ss)");
        }

        //TO-DO: check if the reservation end is before or equal to close
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {return customer;}
    public String getId() {return number.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(number, that.number)
                && Objects.equals(date, that.date)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(groupSize, that.groupSize)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, startTime, groupSize, customer, restaurant);
    }
}
