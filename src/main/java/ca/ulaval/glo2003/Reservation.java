package ca.ulaval.glo2003;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.UUID;

public class Reservation {

    private final UUID id = UUID.randomUUID();
    private LocalDate date;
    private LocalTime startTime;
    private Integer groupSize;
    private Customer customer;

    public Reservation(String date, String startTime, Integer groupSize, Customer customer) {
        setDate(date);
        setStartTime(startTime);
        setGroupSize(groupSize);
        this.customer = customer;
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
            LocalTime baseTime = this.startTime.withSecond(0);
            this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

            if (this.startTime.getMinute() <= 15) {
                this.startTime = this.startTime.withMinute(15);
            }
            else if (this.startTime.getMinute() <= 45) {
                this.startTime = this.startTime.withMinute(15);
            }
            else  {
                this.startTime = this.startTime.withMinute(0);
                this.startTime = this.startTime.plusHours(1);
            }

            if (this.startTime.isAfter(baseTime.withMinute(45))) {
                this.startTime = this.startTime.withMinute(0);
                this.startTime = this.startTime.plusHours(1);
            }
            else if (this.startTime.isAfter(baseTime.withMinute(30))) {
                this.startTime = this.startTime.withMinute(45);
            }
            else if (this.startTime.isAfter(baseTime.withMinute(15))){
                this.startTime = this.startTime.withMinute(30);
            }
            else if (this.startTime.isAfter(baseTime.withMinute(0))){
                this.startTime = this.startTime.withMinute(15);
            }
            this.startTime = this.startTime.withSecond(0);

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
}
