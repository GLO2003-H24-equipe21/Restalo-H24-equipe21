package ca.ulaval.glo2003.data.mongo.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("restaurants")
public class RestaurantMongo {
    @Id
    public String id;
}
