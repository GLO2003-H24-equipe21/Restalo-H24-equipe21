package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.SearchDto;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.entities.SearchOpened;

public class SearchMapper {

    public Search fromDto(SearchDto searchDto) {
        SearchOpened searchOpened = new SearchOpenedMapper().fromDto(searchDto.searchOpened);

        return new Search(searchDto.name, searchOpened);
    }
}
