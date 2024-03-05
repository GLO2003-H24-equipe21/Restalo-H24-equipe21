package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class RestaurantHoursMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public RestaurantHoursDto toDto(RestaurantHours restaurantHours) {
        RestaurantHoursDto dto = new RestaurantHoursDto();
        dto.open = restaurantHours.getOpen().format(dateTimeFormatter);
        dto.close = restaurantHours.getClose().format(dateTimeFormatter);
        return dto;
    }
}
