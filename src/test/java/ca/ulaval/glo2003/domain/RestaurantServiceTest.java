package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantReservationsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    private static final String OWNER_ID = "1234";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;

    RestaurantService restaurantService;
    @Mock
    RestaurantFactory restaurantFactory;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    RestaurantReservations restaurantReservations;
    @Mock
    RestaurantHours restaurantHours;
    @Mock
    RestaurantReservationsFactory restaurantReservationsFactory;
    @Mock
    RestaurantHoursFactory restaurantHoursFactory;

    Restaurant restaurant;
    RestaurantDto restaurantDto;

}