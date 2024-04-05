package ca.ulaval.glo2003.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.RestaurantHoursMongo;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import java.time.LocalTime;

public class RestaurantHoursMongoMapper {

    public RestaurantHoursMongo toMongo(RestaurantHours restaurantHours) {
        String open = restaurantHours.getOpen().toString();
        String close = restaurantHours.getClose().toString();
        return new RestaurantHoursMongo(open, close);
    }

    public RestaurantHours fromMongo(RestaurantHoursMongo restaurantHours) {
        LocalTime open = LocalTime.parse(restaurantHours.open);
        LocalTime close = LocalTime.parse(restaurantHours.close);
        return new RestaurantHours(open, close);
    }
}
