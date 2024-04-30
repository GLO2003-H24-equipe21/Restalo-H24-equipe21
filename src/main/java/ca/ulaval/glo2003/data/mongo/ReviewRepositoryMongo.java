package ca.ulaval.glo2003.data.mongo;

import static dev.morphia.query.filters.Filters.*;

import ca.ulaval.glo2003.data.mongo.entities.ReviewMongo;
import ca.ulaval.glo2003.data.mongo.mappers.ReviewMongoMapper;
import ca.ulaval.glo2003.domain.ReviewRepository;
import ca.ulaval.glo2003.domain.entities.Review;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ReviewRepositoryMongo implements ReviewRepository {
    private final Datastore datastore;
    private final ReviewMongoMapper reviewMongoMapper;
    private final DateTimeFormatter dateTimeFormatter;

    public ReviewRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
        reviewMongoMapper = new ReviewMongoMapper();
        dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Override
    public void add(Review review) {
        datastore.save(reviewMongoMapper.toMongo(review));
    }

    @Override
    public List<Review> searchReviews(
            String restaurantId, List<Integer> ratings, LocalDate from, LocalDate to) {
        Query<ReviewMongo> query =
                datastore.find(ReviewMongo.class).filter(eq("restaurantId", restaurantId));

        if (!ratings.isEmpty()) {
            query = query.filter(in("rating", ratings));
        }
        if (!Objects.isNull(from)) {
            query = query.filter(gte("date", from.format(dateTimeFormatter)));
        }
        if (!Objects.isNull(to)) {
            query = query.filter(lte("date", to.format(dateTimeFormatter)));
        }

        return query.stream().map(reviewMongoMapper::fromMongo).toList();
    }
}
