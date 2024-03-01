package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.requests.SearchRestaurantsRequest;
import ca.ulaval.glo2003.domain.SearchService;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class SearchResource {
    private final SearchService searchService;

    public SearchResource(SearchService searchService) {
        this.searchService = searchService;
    }

    @POST
    @Path("/search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRestaurants(SearchRestaurantsRequest searchRequest) {
        List<RestaurantDto> restaurants =
                searchService.searchRestaurants(searchRequest.name, searchRequest.opened);

        return Response.status(Response.Status.OK).entity(restaurants).build();
    }
}
