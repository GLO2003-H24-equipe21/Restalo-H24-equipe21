package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RestaurantHoursFactory {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public RestaurantHours create(String open, String close) {
        LocalTime localTimeOpen = parseOpenHour(open);
        LocalTime localTimeClose = parseCloseHour(close);

        return create(localTimeOpen, localTimeClose);
    }

    private LocalTime parseOpenHour(String open) {
        try {
            return LocalTime.parse(open, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Open time format is not valid (HH:mm:ss)");
        }
    }

    private LocalTime parseCloseHour(String close) {
        try {
            return LocalTime.parse(close, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Open time format is not valid (HH:mm:ss)");
        }
    }

    public RestaurantHours create(LocalTime open, LocalTime close) {
        verifyOpenBeforeClose(open, close);
        verifyTimeIntervalAtLeastOne(open, close);

        return new RestaurantHours(open, close);
    }

    private void verifyOpenBeforeClose(LocalTime open, LocalTime close) {
        if (close.isBefore(open)) throw new IllegalArgumentException("Incoherent opening hours");
    }

    private void verifyTimeIntervalAtLeastOne(LocalTime open, LocalTime close) {
        if (Duration.between(open, close).toHours() < 1) {
            throw new IllegalArgumentException("Restaurant must be open at least 1 hour");
        }
    }
}
