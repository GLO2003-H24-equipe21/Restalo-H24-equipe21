package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

public class CreateRestaurantRequest {
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantReservationsDto reservations;
}
