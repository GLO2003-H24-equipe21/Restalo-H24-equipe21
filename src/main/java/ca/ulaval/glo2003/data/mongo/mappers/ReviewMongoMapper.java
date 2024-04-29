package ca.ulaval.glo2003.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.ReviewMongo;
import ca.ulaval.glo2003.domain.entities.Review;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReviewMongoMapper {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public ReviewMongo toMongo(Review review) {
        return new ReviewMongo(
                review.getId(),
                review.getRestaurantId(),
                review.getRating(),
                review.getComment(),
                review.getDate().format(dateTimeFormatter));
    }

    public Review fromMongo(ReviewMongo review) {
        return new Review(
                review.id,
                review.restaurantId,
                review.rating,
                review.comment,
                LocalDate.parse(review.date, dateTimeFormatter));
    }
}
