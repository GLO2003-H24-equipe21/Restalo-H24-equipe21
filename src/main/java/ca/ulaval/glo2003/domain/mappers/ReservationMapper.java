package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ReservationMapper {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private final DateTimeFormatter startTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public Reservation fromDto(ReservationDto dto) {
        Objects.requireNonNull(dto.date, "Date of reservation must be provided");
        Objects.requireNonNull(dto.time.start, "Start time of reservation must be provided");
        Objects.requireNonNull(dto.groupSize, "Group size must be provided");
        Objects.requireNonNull(dto.customer, "Customer must be provided");

        LocalDate date = parseDate(dto.date);
        LocalTime startTime = parseStartTime(dto.time.start);
        Customer customer = new CustomerMapper().fromDto(dto.customer);
        Restaurant restaurant = new RestaurantMapper().fromDto(dto.restaurant);

        return new Reservation(date, startTime, dto.groupSize, customer, restaurant);
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date format is not valid (yyyy-MM-dd)");
        }
    }

    private LocalTime parseStartTime(String startTime) {
        try {
            return LocalTime.parse(startTime, startTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Start time format is not valid (HH:mm:ss)");
        }
    }

    public ReservationDto toDto(Reservation reservation) {
        return null;
    }
}
