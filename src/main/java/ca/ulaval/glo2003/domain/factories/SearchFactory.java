package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.entities.SearchOpened;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class SearchFactory {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public Search create(String name, String openedFrom, String openedTo) {
        String nameNotNull = parseName(name);
        LocalTime openedFromNotNull = parseOpenedFrom(openedFrom);
        LocalTime openedToNotNull = parseOpenedTo(openedTo);

        return new Search(nameNotNull, new SearchOpened(openedFromNotNull, openedToNotNull));
    }

    private String parseName(String name) {
        return Objects.requireNonNullElse(name, "*");
    }

    private LocalTime parseOpenedFrom(String openedFrom) {
        if (Objects.nonNull(openedFrom)) {
            try {
                return LocalTime.parse(openedFrom, dateTimeFormatter);
            } catch (DateTimeParseException exception) {
                throw new IllegalArgumentException(
                        "Search 'from' time format is not valid (HH:mm:ss)");
            }
        }
        return LocalTime.of(0, 0, 0);
    }

    private LocalTime parseOpenedTo(String openedTo) {
        if (Objects.nonNull(openedTo)) {
            try {
                return LocalTime.parse(openedTo, dateTimeFormatter);
            } catch (DateTimeParseException exception) {
                throw new IllegalArgumentException(
                        "Search 'to' time format is not valid (HH:mm:ss)");
            }
        }
        return LocalTime.of(23, 59, 59);
    }
}
