package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class RestaurantHoursMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public RestaurantHours fromDto(RestaurantHoursDto dto) {
        Objects.requireNonNull(dto.open, "Open time must be provided");
        Objects.requireNonNull(dto.close, "Close time must be provided");

        LocalTime open = parseOpenHour(dto.open);
        LocalTime close = parseCloseHour(dto.close);

        return new RestaurantHours(open, close);
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

    public RestaurantHoursDto toDto(RestaurantHours restaurantHours) {
        RestaurantHoursDto dto = new RestaurantHoursDto();
        dto.open = restaurantHours.getOpen().format(dateTimeFormatter);
        dto.close = restaurantHours.getClose().format(dateTimeFormatter);
        return dto;
    }
}
