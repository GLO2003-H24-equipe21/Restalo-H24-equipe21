package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.requests.CreateReviewRequest;
import ca.ulaval.glo2003.domain.ReviewService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("")
public class ReviewResource {
    private final ReviewService reviewService;

    public ReviewResource(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @POST
    @Path("restaurants/{id}/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReview(@Context UriInfo uriInfo, @PathParam("id") String restaurantId, @Valid CreateReviewRequest reviewRequest) {
        String reviewId = reviewService.createReview(restaurantId, reviewRequest.rating, reviewRequest.comment);

        return Response.status(Response.Status.CREATED)
                .header(
                        "Location",
                        String.format("%sreviews/%s", uriInfo.getBaseUri(), reviewId))
                .build();
    }
}
