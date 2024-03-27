package ca.ulaval.glo2003.data.mongo.entities;

public class RestaurantHoursMongo {
    public String open;
    public String close;

    public RestaurantHoursMongo(String open, String close) {
        this.open = open;
        this.close = close;
    }
}
