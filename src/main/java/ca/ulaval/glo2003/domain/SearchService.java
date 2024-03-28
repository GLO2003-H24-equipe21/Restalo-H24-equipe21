package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.SearchOpenedPojo;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import java.util.List;
import java.util.Objects;

public class SearchService {
    private final RestaurantRepository restaurantRepository;
    private final SearchFactory searchFactory;

    public SearchService(RestaurantRepository restaurantRepository, SearchFactory searchFactory) {
        this.restaurantRepository = restaurantRepository;
        this.searchFactory = searchFactory;
    }

    public List<Restaurant> searchRestaurants(String name, SearchOpenedPojo searchOpenedPojo) {
        SearchOpenedPojo searchOpened =
                Objects.requireNonNullElse(searchOpenedPojo, new SearchOpenedPojo(null, null));
        Search search = searchFactory.create(name, searchOpened.from, searchOpened.to);

        return restaurantRepository.searchRestaurants(search);
    }
}
