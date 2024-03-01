package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.SearchOpenedDto;
import ca.ulaval.glo2003.domain.entities.SearchOpened;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SearchOpenedMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public SearchOpened fromDto(SearchOpenedDto dto) {
        LocalTime from = parseFrom(dto.from);
        LocalTime to = parseTo(dto.to);

        return new SearchOpened(from, to);
    }

    private LocalTime parseFrom(String from) {
        try {
            return LocalTime.parse(from, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Search 'to' time format is not valid (HH:mm:ss)");
        }
    }

    private LocalTime parseTo(String to) {
        try {
            return LocalTime.parse(to, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Search 'from' format is not valid (HH:mm:ss)");
        }
    }

    public SearchOpenedDto toDto(SearchOpened searchOpened) {
        SearchOpenedDto dto = new SearchOpenedDto();
        dto.from = searchOpened.getFrom().format(dateTimeFormatter);
        dto.to = searchOpened.getTo().format(dateTimeFormatter);
        return dto;
    }
}
