package ca.ulaval.glo2003.domain.dto;

public class RestaurantDto {
    public String ownerId;
    public String id;
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantReservationsDto reservations;
}
