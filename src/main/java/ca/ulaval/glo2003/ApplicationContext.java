package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.ReservationResource;
import ca.ulaval.glo2003.api.RestaurantResource;
import ca.ulaval.glo2003.api.ReviewResource;
import ca.ulaval.glo2003.api.SearchResource;
import ca.ulaval.glo2003.data.DatastoreProvider;
import ca.ulaval.glo2003.data.inmemory.*;
import ca.ulaval.glo2003.data.mongo.*;
import ca.ulaval.glo2003.domain.*;
import ca.ulaval.glo2003.domain.factories.*;

public class ApplicationContext {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    public ApplicationContext() {
        String persistenceProperty = System.getProperty("persistence", "inmemory");
        switch (persistenceProperty) {
            case "inmemory" -> {
                restaurantRepository = new RestaurantRepositoryInMemory();
                reservationRepository = new ReservationRepositoryInMemory();
                reviewRepository = new ReviewRepositoryInMemory();
            }
            case "mongo" -> {
                DatastoreProvider datastoreProvider = new DatastoreProvider();
                restaurantRepository = new RestaurantRepositoryMongo(datastoreProvider.provide());
                reservationRepository = new ReservationRepositoryMongo(datastoreProvider.provide());
                reviewRepository = new ReviewRepositoryMongo(datastoreProvider.provide());
            }
            default ->
                    throw new IllegalArgumentException(
                            "The 'persistence' system property should be 'inmemory' or 'mongo'");
        }
    }

    public RestaurantResource getRestaurantResource() {
        RestaurantFactory restaurantFactory = new RestaurantFactory();
        RestaurantHoursFactory restaurantHoursFactory = new RestaurantHoursFactory();
        RestaurantConfigurationFactory restaurantConfigurationFactory =
                new RestaurantConfigurationFactory();

        RestaurantService restaurantService =
                new RestaurantService(
                        restaurantRepository,
                        reservationRepository,
                        restaurantFactory,
                        restaurantHoursFactory,
                        restaurantConfigurationFactory);

        return new RestaurantResource(restaurantService);
    }

    public ReservationResource getReservationResource() {
        ReservationFactory reservationFactory = new ReservationFactory();
        CustomerFactory customerFactory = new CustomerFactory();

        ReservationService reservationService =
                new ReservationService(
                        reservationRepository,
                        restaurantRepository,
                        reservationFactory,
                        customerFactory);

        return new ReservationResource(reservationService);
    }

    public SearchResource getSearchResource() {
        SearchFactory searchFactory = new SearchFactory();

        SearchService searchService =
                new SearchService(
                        restaurantRepository,
                        reservationRepository,
                        reviewRepository,
                        searchFactory);

        return new SearchResource(searchService);
    }

    public ReviewResource getReviewResource() {
        ReviewFactory reviewFactory = new ReviewFactory();

        ReviewService reviewService = new ReviewService(reviewRepository, restaurantRepository, reviewFactory);

        return new ReviewResource(reviewService);
    }
}
