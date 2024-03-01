package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.SearchOpenedDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchService {
    private final RestaurantRepository restaurantRepository;
    private final SearchFactory searchFactory;
    private final RestaurantMapper restaurantMapper;

    public SearchService(RestaurantRepository restaurantRepository, SearchFactory searchFactory) {
        this.restaurantRepository = restaurantRepository;
        this.searchFactory = searchFactory;
        this.restaurantMapper = new RestaurantMapper();
    }

    public List<RestaurantDto> searchRestaurants(String name, SearchOpenedDto searchOpenedDto) {
        SearchOpenedDto searchOpened =
                Objects.requireNonNullElse(searchOpenedDto, new SearchOpenedDto());
        Search search = searchFactory.create(name, searchOpened.from, searchOpened.to);

        List<Restaurant> restaurants = restaurantRepository.searchRestaurants(search);

        return restaurants.stream().map(restaurantMapper::toDto).collect(Collectors.toList());
    }
}
