package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.ReservationResource;
import ca.ulaval.glo2003.api.RestaurantResource;
import ca.ulaval.glo2003.api.SearchResource;
import ca.ulaval.glo2003.data.DatastoreProvider;
import ca.ulaval.glo2003.data.inmemory.ReservationRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.data.mongo.ReservationRepositoryMongo;
import ca.ulaval.glo2003.data.mongo.RestaurantRepositoryMongo;
import ca.ulaval.glo2003.domain.*;
import ca.ulaval.glo2003.domain.factories.*;

public class ApplicationContext {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public ApplicationContext() {
        String persistenceProperty = System.getProperty("persistence", "inmemory");
        if (persistenceProperty.equals("inmemory")) {
            restaurantRepository = new RestaurantRepositoryInMemory();
            reservationRepository = new ReservationRepositoryInMemory();
        } else if (persistenceProperty.equals("mongo")) {
            DatastoreProvider datastoreProvider = new DatastoreProvider();
            restaurantRepository = new RestaurantRepositoryMongo(datastoreProvider.provide());
            reservationRepository = new ReservationRepositoryMongo(datastoreProvider.provide());
        } else {
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

        SearchService searchService = new SearchService(restaurantRepository, reservationRepository, searchFactory);

        return new SearchResource(searchService);
    }
}
