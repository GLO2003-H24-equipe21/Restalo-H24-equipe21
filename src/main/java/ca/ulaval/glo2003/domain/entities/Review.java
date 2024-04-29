package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Review {
    private final String id;
    private final String restaurantId;
    private final Integer rating;
    private final String comment;
    private final LocalDate date;

    public Review(String id, String restaurantId, Integer rating, String comment, LocalDate date) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id)
                && Objects.equals(restaurantId, review.restaurantId)
                && Objects.equals(rating, review.rating)
                && Objects.equals(comment, review.comment)
                && Objects.equals(date, review.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantId, rating, comment, date);
    }
}
