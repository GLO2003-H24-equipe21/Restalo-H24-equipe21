package ca.ulaval.glo2003.api;

import static ca.ulaval.glo2003.Main.BASE_URI;

import ca.ulaval.glo2003.api.mappers.RestaurantResponseMapper;
import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.api.responses.RestaurantResponse;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;

@Path("")
public class RestaurantResource {

    private final RestaurantService restaurantService;
    private final RestaurantResponseMapper restaurantResponseMapper;

    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        restaurantResponseMapper = new RestaurantResponseMapper();
    }

    @GET
    @Path("restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(
            @PathParam("id") String restaurantId, @HeaderParam("Owner") String ownerId) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        RestaurantDto restaurantDto = restaurantService.getRestaurant(restaurantId, ownerId);
        RestaurantResponse restaurantResponse = restaurantResponseMapper.fromDto(restaurantDto);

        return Response.status(Response.Status.OK).entity(restaurantResponse).build();
    }

    @Path("restaurants")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(
            @HeaderParam("Owner") String ownerId,
            @Valid CreateRestaurantRequest restaurantRequest) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        String restaurantId =
                restaurantService.createRestaurant(
                        ownerId,
                        restaurantRequest.name,
                        restaurantRequest.capacity,
                        restaurantRequest.hours,
                        restaurantRequest.reservations);

        return Response.status(Response.Status.CREATED)
                .header("Location", String.format("%srestaurants/%s", BASE_URI, restaurantId))
                .build();
    }

    @GET
    @Path("restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRestaurants(@HeaderParam("Owner") String ownerId) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        List<RestaurantDto> restaurants = restaurantService.listRestaurants(ownerId);

        return Response.status(Response.Status.OK).entity(restaurants).build();
    }
}
