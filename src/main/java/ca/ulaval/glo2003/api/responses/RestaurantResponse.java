package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;

public class RestaurantResponse {
    public String id;
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantReservationsDto reservations;
}
