package ca.ulaval.glo2003.api;

import static ca.ulaval.glo2003.Main.BASE_URI;

import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.api.requests.ReservationRequest;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@Path("")
public class RestaurantResource {

    Map<String, List<String>> ownerIdToRestaurantsId = new HashMap<>();
    Map<String, Restaurant> restaurantIdToRestaurant = new HashMap<>();
    Map<String, String> restaurantIdToOwnerId = new HashMap<>();
    Map<String, Reservation> reservationNumberToReservation = new HashMap<>();

    Map<String, String> reservationNumberToRestaurantID = new HashMap<>();

    @GET
    @Path("restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(
            @PathParam("id") String restaurantId, @HeaderParam("Owner") String ownerId) {
        if (ownerId == null) throw new NullPointerException("Owner id must be provided");
        if (!restaurantIdToRestaurant.containsKey(restaurantId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (!restaurantIdToOwnerId.get(restaurantId).equals(ownerId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK)
                .entity(restaurantIdToRestaurant.get(restaurantId))
                .build();
    }

    @Path("restaurants")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(
            @HeaderParam("Owner") String owner, CreateRestaurantRequest restaurant) {
        if (owner == null) throw new NullPointerException("Owner id must be provided");
        Restaurant entity =
                new Restaurant(
                        restaurant.name,
                        restaurant.capacity,
                        restaurant.hours.open,
                        restaurant.hours.close);
        addRestaurant(entity, owner);
        return Response.status(Response.Status.CREATED)
                .header("Location", String.format("%srestaurants/%s", BASE_URI, entity.getId()))
                .build();
    }

    private void addRestaurant(Restaurant entity, String ownerId) {
        if (!ownerIdToRestaurantsId.containsKey(ownerId)) {
            ownerIdToRestaurantsId.put(ownerId, new ArrayList<>());
        }
        ownerIdToRestaurantsId.get(ownerId).add(entity.getId());
        restaurantIdToRestaurant.put(entity.getId(), entity);
        restaurantIdToOwnerId.put(entity.getId(), ownerId);
    }

    @GET
    @Path("restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRestaurants(@HeaderParam("Owner") String ownerId) {
        if (ownerId == null) throw new NullPointerException("Owner id must be provided");
        if (!ownerIdToRestaurantsId.containsKey(ownerId)) {
            return Response.status(Response.Status.OK).entity(new ArrayList<>()).build();
        }
        List<Restaurant> restaurants =
                ownerIdToRestaurantsId.get(ownerId).stream()
                        .map(restaurantIdToRestaurant::get)
                        .collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(restaurants).build();
    }

//    post a reservation to a restaurant
    @POST
    @Path("restaurants/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@PathParam("id") String restaurantId, ReservationRequest reservation) {
        Reservation entity =
                new Reservation(
                        reservation.date,
                        reservation.startTime,
                        reservation.groupSize,
                        reservation.customer,
                        restaurantIdToRestaurant.get(restaurantId));
        addReservation(entity, restaurantId);
        return Response.status(Response.Status.CREATED)
                .header("Location", String.format("%srestaurants/%s", BASE_URI, entity.getId()))
                .build();
    }

    private void addReservation(Reservation entity, String restaurantId) {
        reservationNumberToReservation.put(entity.getId(), entity);
        reservationNumberToRestaurantID.put(entity.getId(), restaurantId);
    }
}
