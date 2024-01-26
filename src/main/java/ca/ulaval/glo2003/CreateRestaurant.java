package ca.ulaval.glo2003;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("restaurants")
public class CreateRestaurant {

    @POST
    @Path("restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRestaurant(Restaurant restaurant) {
        return Response.ok(restaurant).build();
    }
}
