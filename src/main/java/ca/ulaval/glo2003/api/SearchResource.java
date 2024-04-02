package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.mappers.AvailabilityResponseMapper;
import ca.ulaval.glo2003.api.mappers.UserRestaurantResponseMapper;
import ca.ulaval.glo2003.api.requests.SearchRestaurantsRequest;
import ca.ulaval.glo2003.domain.SearchService;
import ca.ulaval.glo2003.domain.entities.Availability;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("")
public class SearchResource {
    private final SearchService searchService;
    private final UserRestaurantResponseMapper restaurantResponseMapper;
    private final AvailabilityResponseMapper availabilityResponseMapper;

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
        restaurantResponseMapper = new UserRestaurantResponseMapper();
        availabilityResponseMapper = new AvailabilityResponseMapper();
    }

    @POST
    @Path("search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response searchRestaurants(SearchRestaurantsRequest searchRequest) {
        List<Restaurant> restaurants =
                searchService.searchRestaurants(searchRequest.name, searchRequest.opened);

        return Response.status(Response.Status.OK)
                .entity(
                        restaurants.stream()
                                .map(restaurantResponseMapper::from)
                                .collect(Collectors.toList()))
                .build();
    }

    @GET
    @Path("restaurants/{id}/availabilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAvailabilities(
            @PathParam("id") String restaurantId, @QueryParam("date") String date) {
        Objects.requireNonNull(date, "Date query param must be provided");

        List<Availability> availabilities =
                searchService.searchAvailabilities(restaurantId, parseDate(date));

        return Response.status(Response.Status.OK)
                .entity(
                        availabilities.stream()
                                .map(availabilityResponseMapper::from)
                                .collect(Collectors.toList()))
                .build();
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Date format is not valid (YYYY-MM-DD)");
        }
    }
}
