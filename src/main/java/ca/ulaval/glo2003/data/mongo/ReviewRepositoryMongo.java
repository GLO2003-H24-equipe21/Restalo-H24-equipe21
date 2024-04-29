package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.data.mongo.entities.ReviewMongo;
import ca.ulaval.glo2003.data.mongo.mappers.ReviewMongoMapper;
import ca.ulaval.glo2003.domain.ReviewRepository;
import ca.ulaval.glo2003.domain.entities.Review;
import dev.morphia.Datastore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static dev.morphia.query.filters.Filters.*;

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

    }

    @Override
    public List<Review> searchReviews(String restaurantId, List<Integer> ratings, LocalDate from, LocalDate to) {
        return datastore.find(ReviewMongo.class)
                .filter(eq("restaurantId", restaurantId))
                .filter(in("rating", ratings))
                .filter(gte("from", from.format(dateTimeFormatter)))
                .filter(lte("to", to.format(dateTimeFormatter)))
                .stream().map(reviewMongoMapper::fromMongo)
                .toList();
    }
}
