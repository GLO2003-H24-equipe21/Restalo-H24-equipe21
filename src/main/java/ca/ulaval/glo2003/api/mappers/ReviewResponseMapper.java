package ca.ulaval.glo2003.api.mappers;

import ca.ulaval.glo2003.api.responses.ReviewResponse;
import ca.ulaval.glo2003.domain.entities.Review;
import java.time.format.DateTimeFormatter;

public class ReviewResponseMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getDate().format(dateTimeFormatter));
    }
}
