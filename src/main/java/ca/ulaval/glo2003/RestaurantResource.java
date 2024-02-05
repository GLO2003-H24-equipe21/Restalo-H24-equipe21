package ca.ulaval.glo2003;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class RestaurantResource {
    Map<String, Restaurant> restaurantIdToRestaurant = new HashMap<>();
    Map<String, String> restaurantIdToOwnerId = new HashMap<>();


    @Path("/restaurants/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@PathParam("id") String restaurantId, @HeaderParam("Owner") String ownerId)
    {
        if (ownerId == null) throw new NullPointerException("Owner is missing");
        if (restaurantIdToRestaurant.get(restaurantId).getCapacity() == 0) {
            throw new NullPointerException("Capacity is missing");
        }
        if (restaurantIdToRestaurant.get(restaurantId).getOpeningTime() == null){
            throw new NullPointerException("Opening time is missing");
        }
        if (restaurantIdToRestaurant.get(restaurantId).getClosingTime() == null){
            throw new NullPointerException("Closing time is missing");
        }
        if (restaurantId == null || !restaurantIdToRestaurant.containsKey(restaurantId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("The restaurant does not exist.")
                    .build();
        }
        if (!restaurantIdToOwnerId.get(restaurantId).equals(ownerId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("The restaurant does not belong to the owner")
                    .build();
        }
        return Response.status(Response.Status.OK)
                .entity(restaurantIdToRestaurant.get(restaurantId))
                .build();
    }

    @Path("/restaurants")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(@HeaderParam("Owner") String owner,Restaurant restaurant) {
        URI uri = URI.create("http://0.0.0.0:8080/products");
        boolean valid_parameters = true; //il faut ajouter les code pour vérifier si les parametres sont valides
        boolean missing_parameters = false;

        System.out.println(restaurant.getOwner());
        System.out.println(restaurant.getCapacity());
        if (valid_parameters && !missing_parameters)
        {
            return Response.created(uri).header("Location", "Created Resource URI").build();
        }
        return Response.status(400).build(); // il faut ajouter le code pour créer les json de réponse demandée.

    }

    @Path("/restaurants")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurantList()
    {
        return Response.ok(new RestaurantResponse("This is a restaurant list")).build();
    }


}
