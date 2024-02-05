package ca.ulaval.glo2003;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ca.ulaval.glo2003.Main.BASE_URI;

@Path("")
public class RestaurantResource {
    Map<String, List<String>> ownerIdToRestaurants = new HashMap<>();
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
        /*if (restaurantIdToRestaurant.get(restaurantId).getOpeningTime() == null){
            throw new NullPointerException("Opening time is missing");
        }
        if (restaurantIdToRestaurant.get(restaurantId).getClosingTime() == null){
            throw new NullPointerException("Closing time is missing");
        }*/
        if (!restaurantIdToRestaurant.containsKey(restaurantId)) {
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
    public Response createRestaurant(@HeaderParam("Owner") String owner,RestaurantRequest restaurant) {
        if (owner == null) throw new NullPointerException("Owner id should be provided");
        Restaurant entity = new Restaurant(
                restaurant.name,
                restaurant.capacity,
                restaurant.hours.open,
                restaurant.hours.close
        );
        addRestaurant(entity, owner);
        return Response.status(Response.Status.CREATED)
                .header("Location", String.format("%srestaurants/%s", BASE_URI, entity.getId()))
                .build();


    }

    private void addRestaurant(Restaurant entity, String ownerId) {
        if (!ownerIdToRestaurants.containsKey(ownerId)){
            ownerIdToRestaurants.put(ownerId, new ArrayList<>());
        }
        ownerIdToRestaurants.get(ownerId).add(entity.getId());
        restaurantIdToRestaurant.put(entity.getId(), entity);
        restaurantIdToOwnerId.put(entity.getId(), ownerId);
    }

    @Path("/restaurants")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurantList()
    {
        return Response.ok(new RestaurantResponse("This is a restaurant list")).build();
    }


}
