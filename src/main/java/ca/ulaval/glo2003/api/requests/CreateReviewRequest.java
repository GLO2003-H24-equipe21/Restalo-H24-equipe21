package ca.ulaval.glo2003.api.requests;

import jakarta.validation.constraints.NotNull;

public class CreateReviewRequest {

    @NotNull(message = "Review rating must be provided") public Integer rating;

    @NotNull(message = "Review comment must be provided") public String comment;
}
