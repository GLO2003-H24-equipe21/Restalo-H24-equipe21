package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.responses.ReservationResponse;
import ca.ulaval.glo2003.domain.ReservationService;
import ca.ulaval.glo2003.domain.dto.ReservationDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ReservationResource {
    private final ReservationService reservationService;
    private final ReservationResponseMapper reservationResponseMapper;

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
        reservationResponseMapper = new ReservationResponseMapper();
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
