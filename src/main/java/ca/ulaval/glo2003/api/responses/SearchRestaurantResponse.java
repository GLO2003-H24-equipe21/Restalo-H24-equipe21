package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;

public class SearchRestaurantResponse {
    public String id;
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
}
