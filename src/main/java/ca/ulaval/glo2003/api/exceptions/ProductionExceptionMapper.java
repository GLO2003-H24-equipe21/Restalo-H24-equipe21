package ca.ulaval.glo2003.api.exceptions;

import ca.ulaval.glo2003.api.responses.ErrorResponse;
import io.sentry.Sentry;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ProductionExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        Sentry.captureException(exception);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("UNEXPECTED_ERROR", exception.getMessage()))
                .build();
    }
}
