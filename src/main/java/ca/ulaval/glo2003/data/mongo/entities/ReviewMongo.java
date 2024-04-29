package ca.ulaval.glo2003.data.mongo.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("reviews")
public class ReviewMongo {
    @Id public String id;
    public String restaurantId;
    public Integer rating;
    public String comment;
    public String date;

    public ReviewMongo() {}

    public ReviewMongo(
            String id, String restaurantId, Integer rating, String comment, String date) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }
}
