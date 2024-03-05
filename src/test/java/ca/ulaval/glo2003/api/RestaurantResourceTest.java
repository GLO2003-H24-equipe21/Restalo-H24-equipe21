package ca.ulaval.glo2003.api;
import ca.ulaval.glo2003.ApplicationContext;
import ca.ulaval.glo2003.Main;
import ca.ulaval.glo2003.api.exceptions.ConstraintViolationExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.IllegalArgumentExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.NullPointerExceptionMapper;
import ca.ulaval.glo2003.api.exceptions.RuntimeExceptionMapper;
import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.api.responses.ErrorResponse;
import ca.ulaval.glo2003.api.responses.RestaurantResponse;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantReservationsFactory;
import ca.ulaval.glo2003.domain.mappers.RestaurantHoursMapper;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import ca.ulaval.glo2003.domain.mappers.RestaurantReservationsMapper;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class RestaurantResourceIntegratedTest{
    private static final String OWNER_ID = "1234";
    private static final String INVALID_OWNER_ID = "ABCD";
    private static final String INVALID_RESTAURANT_ID = "32JFD323";
    private static final String RESTAURANT_NAME = "Paccini";
    private static final int CAPACITY = 34;
    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final int DURATION = 120;
    private static final RestaurantHours RESTAURANT_HOURS = new RestaurantHours(LocalTime.parse(OPEN), LocalTime.parse(CLOSE));
    private static final RestaurantReservations RESTAURANT_RESERVATIONS = new RestaurantReservations(DURATION);
    private static final Restaurant RESTAURANT = new Restaurant(OWNER_ID, RESTAURANT_NAME, CAPACITY, RESTAURANT_HOURS, RESTAURANT_RESERVATIONS);
    private static final RestaurantMapper RESTAURANT_MAPPER = new RestaurantMapper();
    static RestaurantRepository restaurantRepository = new RestaurantRepository();

    static RestaurantFactory restaurantFactory = new RestaurantFactory();

    static RestaurantHoursFactory restaurantHoursFactory = new RestaurantHoursFactory();

    static RestaurantReservationsFactory restaurantReservationsFactory = new RestaurantReservationsFactory();
    private static final String BASE_URI = Main.BASE_URI;

    private static JerseyTestApi api;

    private static final RestaurantService restaurantService = new RestaurantService(restaurantRepository, restaurantFactory, restaurantHoursFactory, restaurantReservationsFactory);

    private static final CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
    private static final CreateRestaurantRequest invalidRestaurantRequest = new CreateRestaurantRequest();

    private static final CreateRestaurantRequest missingRestaurantRequest = new CreateRestaurantRequest();
    private static final RestaurantResponse restaurantResponse = new RestaurantResponse();

    private static String restaurantId;

//    @Override
    protected static Application configure() {
        return new ResourceConfig()
                .register(new RestaurantResource(restaurantService))
                .register(new NullPointerExceptionMapper())
                .register(new IllegalArgumentExceptionMapper())
                .register(new ConstraintViolationExceptionMapper())
                .register(new RuntimeExceptionMapper());
    }

    @BeforeAll
    static void startServer() {
//        Main.main(new String[0]);
        api = new JerseyTestApi(configure());
        api.start();
        Restaurant getRestaurant = new Restaurant(OWNER_ID, RESTAURANT_NAME, CAPACITY, RESTAURANT_HOURS, RESTAURANT_RESERVATIONS);
        RestaurantHoursMapper restaurantHoursMapper = new RestaurantHoursMapper();
        RestaurantReservationsMapper restaurantReservationsMapper = new RestaurantReservationsMapper();

//        restaurantId = restaurantService.createRestaurant(OWNER_ID, RESTAURANT_NAME, CAPACITY,
//                restaurantHoursMapper.toDto(RESTAURANT_HOURS), restaurantReservationsMapper.toDto(RESTAURANT_RESERVATIONS));

        restaurantRepository.add(getRestaurant);

        restaurantRequest.name = "PACINI";
        restaurantRequest.capacity = 30;
        RestaurantHoursDto restaurantHours = new RestaurantHoursDto();
        restaurantHours.open = "13:00:00";
        restaurantHours.close = "18:00:00";
        restaurantRequest.hours = restaurantHours;
        RestaurantReservationsDto reservations = new RestaurantReservationsDto();
        reservations.duration = 60;
        restaurantRequest.reservations = reservations;

        invalidRestaurantRequest.name = "PACINI";
        invalidRestaurantRequest.capacity = -30;
        invalidRestaurantRequest.hours = restaurantHours;
        invalidRestaurantRequest.reservations = reservations;

        missingRestaurantRequest.name = "";
        missingRestaurantRequest.capacity = null;
        missingRestaurantRequest.hours = restaurantHours;
        missingRestaurantRequest.reservations = reservations;

        restaurantResponse.id = restaurantId;
        restaurantResponse.name = RESTAURANT_NAME;
        restaurantResponse.capacity = CAPACITY;
        restaurantResponse.hours = restaurantHoursMapper.toDto(RESTAURANT_HOURS);
        restaurantResponse.reservations = restaurantReservationsMapper.toDto(RESTAURANT_RESERVATIONS);
    }

    @Test
    public void givenValidOwnerId_whenPostRequest_createsRestaurantAndReturns201(){
        Response response = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getStatus()).isEqualTo(201);
        String header = response.getHeaderString(HttpHeaders.LOCATION);
        String[] headerList = header.split("restaurants/", 2);
        Assertions.assertThat(headerList[1]).isNotEmpty();
        Assertions.assertThat(headerList[1]).matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
        response.close();
    }

    @Test void givenMissingOwnerId_whenPostRequest_Returns400AndMissingParameterBody() {
        Response response = api.path("/restaurants")
                .request()
                .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo(new ErrorResponse("MISSING_PARAMETER", "Owner id must be provided").error());
        response.close();
    }

    @Test void givenInvalidParameter_whenPostRequest_Returns400AndInvalidParameterBody() {
        Response response = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(invalidRestaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo(new ErrorResponse("INVALID_PARAMETER", "Invalid Parameter").error());
        response.close();
    }

    @Test void givenMissingParameter_whenPostRequest_Returns400AndInvalidParameterBody() {
        Response response = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(missingRestaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error()).isEqualTo(new ErrorResponse("MISSING_PARAMETER", "Invalid Parameter").error());
        response.close();
    }

    @Test void givenValidParameters_whenGetRestaurantRequest_Returns200AndRestaurantBody() {
        Response response1 = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));
        String header1 = response1.getHeaderString(HttpHeaders.LOCATION);
        String[] headerList = header1.split("restaurants/", 2);
        restaurantId = headerList[1];


        Response response = api.path("/restaurants/" + restaurantId)
                .request()
                .header("Owner", OWNER_ID)
                .get();
//        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.readEntity(ErrorResponse.class)).isEqualTo(new ErrorResponse(" ", "Invalid Parameter"));
        response.close();

    }

    @Test void givenInvalidRestaurantId_whenGetRestaurantRequest_Returns404NotFound() {
        Response response = api.path("/restaurants/" + INVALID_RESTAURANT_ID)
                .request()
                .header("Owner", OWNER_ID)
                .get();
        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test void givenInvalidOwnerId_whenGetRestaurantRequest_Returns404NotFound() {

    }

    @Test void givenMissingParameter_whenGetRestaurantRequest_Returns400AndMissingParameterBody() {

    }

    @Test void givenValidParameters_whenGetRestaurantListRequest_Returns200AndOwnerRestaurantList() {

    }

}