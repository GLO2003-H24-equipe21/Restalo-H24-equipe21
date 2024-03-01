package ca.ulaval.glo2003;

import ca.ulaval.glo2003.api.ReservationResource;
import ca.ulaval.glo2003.api.RestaurantResource;
import ca.ulaval.glo2003.api.SearchResource;
import ca.ulaval.glo2003.data.ReservationRepository;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.SearchService;
import ca.ulaval.glo2003.domain.factories.*;

public class ApplicationContext {
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public ApplicationContext() {
        restaurantRepository = new RestaurantRepository();
        reservationRepository = new ReservationRepository();
    }

    public RestaurantResource getRestaurantResource() {
        RestaurantFactory restaurantFactory = new RestaurantFactory();
        RestaurantHoursFactory restaurantHoursFactory = new RestaurantHoursFactory();
        RestaurantReservationsFactory restaurantReservationsFactory =
                new RestaurantReservationsFactory();

        RestaurantService restaurantService =
                new RestaurantService(
                        restaurantRepository,
                        restaurantFactory,
                        restaurantHoursFactory,
                        restaurantReservationsFactory);

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

        SearchService searchService = new SearchService(restaurantRepository, searchFactory);

        return new SearchResource(searchService);
    }
}
