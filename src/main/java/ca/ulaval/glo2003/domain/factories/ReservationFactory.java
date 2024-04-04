package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.ReservationTime;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReservationFactory {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private final DateTimeFormatter startTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public Reservation create(
            String date,
            String startTime,
            Integer groupSize,
            Customer customer,
            Restaurant restaurant,
            Map<LocalDateTime, Integer> availabilities) {
        LocalDate parsedDate = parseDate(date);
        LocalTime parsedStartTime = parseStartTime(startTime);
        ReservationTime reservationTime =
                new ReservationTime(parsedStartTime, restaurant.getConfiguration().getDuration());

        verifyReservationStartsBeforeRestaurantOpen(
                reservationTime.getStart(), restaurant.getHours().getOpen());
        verifyReservationEndBeforeRestaurantClose(
                reservationTime.getEnd(), restaurant.getHours().getClose());
        verifyGroupSizeAtLeastOne(groupSize);
        verifyAvailabilities(availabilities, parsedDate, reservationTime, groupSize);

        return new Reservation(
                createNumber(),
                parsedDate,
                reservationTime,
                groupSize,
                customer,
                restaurant.getId());
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

    private void verifyReservationStartsBeforeRestaurantOpen(
            LocalTime reservationStart, LocalTime restaurantOpen) {
        if (reservationStart.isBefore(restaurantOpen)) {
            throw new IllegalArgumentException(
                    "Reservation start time precedes restaurant opening time");
        }
    }

    private void verifyReservationEndBeforeRestaurantClose(
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

    private void verifyAvailabilities(
            Map<LocalDateTime, Integer> availabilities,
            LocalDate date,
            ReservationTime time,
            Integer groupSize) {
        List<LocalDateTime> intervals =
                create15MinutesIntervals(date, time.getStart(), time.getEnd());
        for (LocalDateTime dateTime : intervals) {
            if (availabilities.getOrDefault(dateTime, 0) < groupSize) {
                throw new IllegalArgumentException("No availabilities at " + dateTime);
            }
        }
    }

    private List<LocalDateTime> create15MinutesIntervals(
            LocalDate date, LocalTime start, LocalTime end) {
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        for (LocalTime current = start; current.isBefore(end); current = current.plusMinutes(15)) {
            localDateTimes.add(LocalDateTime.of(date, current));
        }
        return localDateTimes;
    }

    private String createNumber() {
        return String.format(
                "%040d",
                new java.math.BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    }
}
