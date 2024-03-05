package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.exceptions.*;
import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.api.requests.RestaurantRequestFixture;
import ca.ulaval.glo2003.api.responses.ErrorResponse;
import ca.ulaval.glo2003.api.responses.RestaurantResponse;
import ca.ulaval.glo2003.api.responses.RestaurantResponseFixture;
import ca.ulaval.glo2003.data.RestaurantRepository;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantReservationsFactory;
import ca.ulaval.glo2003.domain.mappers.RestaurantMapper;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


class RestaurantResourceIntegratedTest {
    private static final Restaurant RESTAURANT = new RestaurantFixture().create();
    private static final String OWNER_ID = RESTAURANT.getOwnerId();

    private static final String OTHER_OWNER_NAME = "Shaker";
    private static final Restaurant RESTAURANT2 = new RestaurantFixture().withName(OTHER_OWNER_NAME).create();

    private static final String OTHER_OWNER_ID = "lola";
    private static final Restaurant OTHER_RESTAURANT = new RestaurantFixture().withOwnerId(OTHER_OWNER_ID).create();

    private static final CreateRestaurantRequest restaurantRequest = new RestaurantRequestFixture().create();
    private static final CreateRestaurantRequest invalidRestaurantRequest = new RestaurantRequestFixture().withInvalidParameter(-30);
    private static final CreateRestaurantRequest missingRestaurantRequest = new RestaurantRequestFixture().withMissingParameter();

    private static final String INVALID_OWNER_ID = "ABCD";
    private static final String INVALID_RESTAURANT_ID = "32JFD323";

    private static final RestaurantMapper RESTAURANT_MAPPER = new RestaurantMapper();
    static RestaurantRepository restaurantRepository = new RestaurantRepository();

    static RestaurantFactory restaurantFactory = new RestaurantFactory();
    static RestaurantHoursFactory restaurantHoursFactory = new RestaurantHoursFactory();
    static RestaurantReservationsFactory restaurantReservationsFactory = new RestaurantReservationsFactory();

    private static JerseyTestApi api;

    private static final RestaurantService restaurantService = new RestaurantService(restaurantRepository, restaurantFactory, restaurantHoursFactory, restaurantReservationsFactory);

    private static final RestaurantResponse restaurantResponse = new RestaurantResponseFixture().create(RESTAURANT.getId());

    private static ArrayList<RestaurantDto> restaurants;


    protected static Application configure() {
        return new ResourceConfig()
                .register(new RestaurantResource(restaurantService))
                .register(new NullPointerExceptionMapper())
                .register(new IllegalArgumentExceptionMapper())
                .register(new ConstraintViolationExceptionMapper())
                .register(new RuntimeExceptionMapper())
                .register(new NotFoundExceptionMapper());
    }

    @BeforeAll
    static void startServer() {
        api = new JerseyTestApi(configure());
        api.start();

        restaurantRepository.add(RESTAURANT);
        restaurantRepository.add(RESTAURANT2);
        restaurantRepository.add(OTHER_RESTAURANT);

        restaurants = new ArrayList<RestaurantDto>();

        restaurants.add(RESTAURANT_MAPPER.toDto(RESTAURANT));
        restaurants.add(RESTAURANT_MAPPER.toDto(RESTAURANT2));
    }

    @Test
    public void givenValidOwnerId_whenPostRequest_createsRestaurantAndReturns201(){
        Response response = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getStatus()).isEqualTo(201);

        String[] headers = response.getHeaderString(HttpHeaders.LOCATION).split("restaurants/", 2);

        Assertions.assertThat(headers[1]).isNotEmpty();
        Assertions.assertThat(headers[1]).matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    @Test void givenMissingOwnerId_whenPostRequest_Returns400AndMissingParameterBody() {
        Response response = api.path("/restaurants")
                .request()
                .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse(
                        "MISSING_PARAMETER",
                        "Owner id must be provided")
                        .error());
    }

    @Test void givenInvalidParameter_whenPostRequest_Returns400AndInvalidParameterBody() {
        Response response = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(invalidRestaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse(
                        "INVALID_PARAMETER",
                        "Invalid Parameter")
                        .error());
    }

    @Test void givenMissingRestaurantParameter_whenPostRequest_Returns400AndInvalidParameterBody() {
        Response response = api.path("/restaurants")
                .request()
                .header("Owner", OWNER_ID)
                .post(Entity.entity(missingRestaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse(
                        "MISSING_PARAMETER",
                        "Missing Parameter").error());
    }

    @Test void givenValidParameters_whenGetRestaurant_Returns200AndRestaurantBody() {
        Response response = api.path("/restaurants/" + RESTAURANT.getId())
                .request()
                .header("Owner", OWNER_ID)
                .get();
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        
        RestaurantResponse receivedRestaurantResponse = response.readEntity(RestaurantResponse.class);
        Assertions.assertThat(receivedRestaurantResponse).isEqualTo(restaurantResponse);
    }

    @Test void givenInvalidRestaurantId_whenGetRestaurant_Returns404NotFound() {
        Response response = api.path("/restaurants/" + INVALID_RESTAURANT_ID)
                .request()
                .header("Owner", OWNER_ID)
                .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test void givenInvalidOwnerId_whenGetRestaurant_Returns404NotFound() {
        Response response = api.path("/restaurants/" + RESTAURANT.getId())
                .request()
                .header("Owner", INVALID_OWNER_ID)
                .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test void givenMissingOwner_whenGetRestaurant_Returns400AndMissingParameterBody() {
        Response response = api.path("/restaurants/" + RESTAURANT.getId())
                .request()
                .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse(
                        "MISSING_PARAMETER",
                        "Missing Parameter")
                        .error());
    }

    @Test void givenValidParameters_whenGetRestaurantList_Returns200AndOwnerRestaurantList() {
        Response response = api.path("/restaurants/")
                .request()
                .header("Owner", OWNER_ID)
                .get();

        ArrayList<RestaurantDto> entities = response.readEntity(new GenericType<ArrayList<RestaurantDto>> () {});

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(entities.size()).isEqualTo(restaurantService.listRestaurants(OWNER_ID).size());
        for (RestaurantDto expectedRestaurant : restaurantService.listRestaurants(OWNER_ID)) {
            assertThat(entities).contains(expectedRestaurant);
        }
    }

    @Test void givenInvalidOwner_whenGetRestaurantList_ReturnsEmptyRestaurantList() {
        Response response = api.path("/restaurants/")
                .request()
                .header("Owner", INVALID_OWNER_ID)
                .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.readEntity(List.class)).isEqualTo(new ArrayList<>());
    }

    @Test void givenMissingOwner_whenGetRestaurantList_Returns400AndMissingParameterBody() {
        Response response = api.path("/restaurants/")
                .request()
                .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse(
                        "MISSING_PARAMETER",
                        "Missing Parameter")
                        .error());
    }
}