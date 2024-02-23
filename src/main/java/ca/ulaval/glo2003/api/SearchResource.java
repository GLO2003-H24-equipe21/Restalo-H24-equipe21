package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.mappers.SearchRestaurantsRequestMapper;
import ca.ulaval.glo2003.api.requests.SearchRestaurantsRequest;
import ca.ulaval.glo2003.domain.SearchService;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;
import ca.ulaval.glo2003.domain.dto.SearchDto;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResource {
    private final SearchService searchService;
    private final SearchRestaurantsRequestMapper searchRestaurantsRequestMapper;

    public SearchResource() {
        searchService = new SearchService();
        searchRestaurantsRequestMapper = new SearchRestaurantsRequestMapper();
    }

    @POST
    @Path("/search/restaurants")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRestaurants(SearchRestaurantsRequest searchRestaurantsRequest) {
        SearchDto searchDto = searchRestaurantsRequestMapper.toDto(searchRestaurantsRequest);
        List<RestaurantDto> restaurants = searchService.searchRestaurants(searchDto);
        return Response.status(Response.Status.OK).entity(restaurants).build();
    }
}
