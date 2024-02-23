package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.SearchDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import ca.ulaval.glo2003.domain.mappers.SearchMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;


public class SearchService {
    private final SearchMapper searchMapper;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;

    public SearchService() {
        searchMapper = new SearchMapper();
        restaurantMapper = new RestaurantMapper();
        restaurantRepository = new RestaurantRepository();
    }

    public List<RestaurantDto> searchRestaurants(SearchDto searchDto) {
        Search search = searchMapper.fromDto(searchDto);

        List<Restaurant> restaurants = restaurantRepository.searchRestaurants(search);

        return restaurants.stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }
}