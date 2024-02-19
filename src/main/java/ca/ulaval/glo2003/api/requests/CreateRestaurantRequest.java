package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.domain.dtos.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantReservationsDto;

public class CreateRestaurantRequest {
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantReservationsDto reservations;
}
