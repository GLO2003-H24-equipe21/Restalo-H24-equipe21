package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ReviewFixture {
    private String id = UUID.randomUUID().toString();
    private String restaurantId = UUID.randomUUID().toString();
    private Integer rating = 4;
    private String comment = "Very good restaurant!";
    private LocalDate date = LocalDate.now().plusDays(1);

    public Review create() {
        return new Review(id, restaurantId, rating, comment, date);
    }

    public ReviewFixture withId(String id) {
        this.id = id;
        return this;
    }

    public ReviewFixture withRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public ReviewFixture withRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public ReviewFixture withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public ReviewFixture withDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
