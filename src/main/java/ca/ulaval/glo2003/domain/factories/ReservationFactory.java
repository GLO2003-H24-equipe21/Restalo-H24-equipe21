package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.ReservationTime;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationFactory {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private final DateTimeFormatter startTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public Reservation create(
            String date,
            String startTime,
            Integer groupSize,
            Customer customer,
            Restaurant restaurant) {
        LocalDate localDateDate = parseDate(date);
        LocalTime localTimeStartTime = parseStartTime(startTime);

        return create(localDateDate, localTimeStartTime, groupSize, customer, restaurant);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Reservation date format is not valid (yyyy-MM-dd)");
        }
    }

    private LocalTime parseStartTime(String startTime) {
        try {
            return LocalTime.parse(startTime, startTimeFormatter);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Reservation start time format is not valid (HH:mm:ss)");
        }
    }

    public Reservation create(
            LocalDate date,
            LocalTime startTime,
            Integer groupSize,
            Customer customer,
            Restaurant restaurant) {
        ReservationTime reservationTime =
                new ReservationTime(startTime, restaurant.getReservations().getDuration());
        verifyReservationStartsBeforeOpen(
                reservationTime.getStart(), restaurant.getHours().getOpen());
        verifyReservationEndBeforeRestoClose(
                reservationTime.getEnd(), restaurant.getHours().getClose());
        verifyGroupSizeAtLeastOne(groupSize);

        return new Reservation(date, reservationTime, groupSize, customer, restaurant);
    }

    private void verifyReservationStartsBeforeOpen(
            LocalTime reservationStart, LocalTime restaurantOpen) {
        if (reservationStart.isBefore(restaurantOpen)) {
            throw new IllegalArgumentException(
                    "Reservation start time precedes restaurant opening time");
        }
    }

    private void verifyReservationEndBeforeRestoClose(
            LocalTime reservationEnd, LocalTime restaurantClose) {
        if (reservationEnd.isAfter(restaurantClose)) {
            throw new IllegalArgumentException(
                    "Reservation end time exceeds restaurant closing time");
        }
    }

    private void verifyGroupSizeAtLeastOne(Integer groupSize) {
        if (groupSize < 1) {
            throw new IllegalArgumentException("Reservation group size must be at least one");
        }
    }
}
