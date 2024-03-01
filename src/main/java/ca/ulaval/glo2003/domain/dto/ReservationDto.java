package ca.ulaval.glo2003.domain.dto;

public class ReservationDto {
    public String number;
    public String date;
    public ReservationTimeDto time;
    public Integer groupSize;
    public CustomerDto customer;
    public RestaurantDto restaurant;
}
