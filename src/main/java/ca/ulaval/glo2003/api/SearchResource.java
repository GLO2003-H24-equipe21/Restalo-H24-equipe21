package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.mappers.UserRestaurantResponseMapper;
import ca.ulaval.glo2003.api.requests.SearchRestaurantsRequest;
import ca.ulaval.glo2003.domain.SearchService;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("")
public class SearchResource {
    private final SearchService searchService;
    private final UserRestaurantResponseMapper restaurantResponseMapper;

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
        restaurantResponseMapper = new UserRestaurantResponseMapper();
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
}
