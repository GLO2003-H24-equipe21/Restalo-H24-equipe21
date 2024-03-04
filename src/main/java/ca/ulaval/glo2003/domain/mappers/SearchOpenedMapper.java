package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.SearchOpenedDto;
import ca.ulaval.glo2003.domain.entities.SearchOpened;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SearchOpenedMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public SearchOpenedDto toDto(SearchOpened searchOpened) {
        SearchOpenedDto dto = new SearchOpenedDto();
        dto.from = searchOpened.getFrom().format(dateTimeFormatter);
        dto.to = searchOpened.getTo().format(dateTimeFormatter);
        return dto;
    }
}
