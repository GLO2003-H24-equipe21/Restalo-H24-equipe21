package ca.ulaval.glo2003.data.mongo.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("reservations")
public class ReservationMongo {
    @Id
    public String number;
}
