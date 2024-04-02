package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.SearchOpenedPojo;
import ca.ulaval.glo2003.domain.entities.Availability;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final SearchFactory searchFactory;

    public SearchService(RestaurantRepository restaurantRepository, ReservationRepository reservationRepository, SearchFactory searchFactory) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.searchFactory = searchFactory;
    }

    public List<Restaurant> searchRestaurants(String name, SearchOpenedPojo searchOpenedPojo) {
        SearchOpenedPojo searchOpened =
                Objects.requireNonNullElse(searchOpenedPojo, new SearchOpenedPojo(null, null));
        Search search = searchFactory.create(name, searchOpened.from, searchOpened.to);

        return restaurantRepository.searchRestaurants(search);
    }

    public List<Availability> searchAvailabilities(String restaurantId, LocalDate date) {
        Restaurant restaurant =
                restaurantRepository
                        .get(restaurantId)
                        .orElseThrow(() -> new NotFoundException("Restaurant does not exist"));
        Map<LocalDateTime, Integer> availabilities =
                reservationRepository
                        .searchAvailabilities(restaurant, date);

        List<Availability> availabilityEntities = new ArrayList<>();
        availabilities.forEach((start, remainingPlace) -> availabilityEntities.add(new Availability(start, remainingPlace)));

        return availabilityEntities;
    }
}
