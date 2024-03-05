package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.mappers.ReservationResponseMapper;
import ca.ulaval.glo2003.api.requests.CreateReservationRequest;
import ca.ulaval.glo2003.api.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("")
public class ReservationResource {
    private final ReservationService reservationService;
    private final ReservationResponseMapper reservationResponseMapper;

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
        reservationResponseMapper = new ReservationResponseMapper();
    }

    @POST
    @Path("restaurants/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(
            @Context UriInfo uriInfo,
            @PathParam("id") String restaurantId,
            @Valid CreateReservationRequest reservationRequest) {
        String reservationNumber =
                reservationService.createReservation(
                        restaurantId,
                        reservationRequest.date,
                        reservationRequest.startTime,
                        reservationRequest.groupSize,
                        reservationRequest.customer);

        return Response.status(Response.Status.CREATED)
                .header(
                        "Location",
                        String.format("%sreservations/%s", uriInfo.getBaseUri(), reservationNumber))
                .build();
    }

    @GET
    @Path("reservations/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservation(@PathParam("number") String number) {
        ReservationDto reservationDto = reservationService.getReservation(number);

        ReservationResponse reservationResponse = reservationResponseMapper.fromDto(reservationDto);

        return Response.status(Response.Status.OK).entity(reservationResponse).build();
    }
}
