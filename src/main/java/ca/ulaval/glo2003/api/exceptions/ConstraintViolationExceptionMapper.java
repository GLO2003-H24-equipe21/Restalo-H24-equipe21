package ca.ulaval.glo2003.api.exceptions;

import ca.ulaval.glo2003.api.responses.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("MISSING_PARAMETER", exception.getMessage()))
                .build();
    }
}