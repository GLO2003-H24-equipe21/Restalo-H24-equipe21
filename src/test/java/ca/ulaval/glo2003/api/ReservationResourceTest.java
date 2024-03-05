package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationResourceTest extends JerseyTest {
    private static final String OWNER_ID = "1234";
    private static final String INVALID_OWNER_ID = "ABCD";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;

    private final static Restaurant RESTAURANT = new Restaurant(OWNER_ID, RESTAURANT_NAME, CAPACITY, new RestaurantHours(LocalTime.parse(OPEN), LocalTime.parse(CLOSE)), new RestaurantReservations(DURATION));



//    private String restaurantId;
//    @Override
//    protected Application configure() {
//        return new ResourceConfig().register(new ReservationResource(ReservationService.class));
//    }
//    @Test
//    public void givenRestaurantId_whenCorrectRequest_thenResponseIs201Created() {
//        Response response = target("/restaurant/").request().get();
//
//    }



}