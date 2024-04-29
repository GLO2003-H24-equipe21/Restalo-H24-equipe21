package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.SearchOpenedPojo;
import ca.ulaval.glo2003.domain.entities.*;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchService {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final SearchFactory searchFactory;

    public SearchService(
            RestaurantRepository restaurantRepository,
            ReservationRepository reservationRepository,
            ReviewRepository reviewRepository,
            SearchFactory searchFactory) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
        this.reviewRepository = reviewRepository;
        this.searchFactory = searchFactory;
    }

    public List<Restaurant> searchRestaurants(String name, SearchOpenedPojo searchOpenedPojo) {
        SearchOpenedPojo searchOpened =
                Objects.requireNonNullElse(searchOpenedPojo, new SearchOpenedPojo(null, null));
        Search search = searchFactory.create(name, searchOpened.from, searchOpened.to);

        return restaurantRepository.searchRestaurants(search);
    }

    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        Restaurant restaurant = getRestaurantIfExists(restaurantId);

        if (!restaurant.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("Restaurant owner id is invalid");
        }

        return reservationRepository.searchReservations(
                restaurantId, parseDate(date), customerName);
    }

    private Restaurant getRestaurantIfExists(String restaurantId) {
        return restaurantRepository
                .get(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant does not exist"));
    }

    private LocalDate parseDate(String date) {
        if (Objects.isNull(date)) return null;
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Date format is not valid (YYYY-MM-DD)");
        }
    }

    public List<Availability> searchAvailabilities(String restaurantId, String date) {
        Restaurant restaurant = getRestaurantIfExists(restaurantId);

        Map<LocalDateTime, Integer> availabilities =
                reservationRepository.searchAvailabilities(restaurant, parseDate(date));

        List<Availability> availabilityEntities = new ArrayList<>();
        availabilities.forEach(
                (start, remainingPlace) ->
                        availabilityEntities.add(new Availability(start, remainingPlace)));

        return availabilityEntities;
    }

    public List<Review> searchReviews(
            String restaurantId, List<String> ratings, String from, String to) {
        getRestaurantIfExists(restaurantId);

        return reviewRepository.searchReviews(
                restaurantId, parseRatings(ratings), parseDate(from), parseDate(to));
    }

    private List<Integer> parseRatings(List<String> ratings) {
        List<Integer> convertedRatings;
        try {
            convertedRatings = ratings.stream().map(Integer::valueOf).collect(Collectors.toList());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Rating format is not valid (integer)");
        }
        if (!convertedRatings.stream().allMatch(num -> num >= 0 && num <= 5)) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        return convertedRatings;
    }
}
