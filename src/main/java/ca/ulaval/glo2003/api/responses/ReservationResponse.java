package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.domain.dtos.CustomerDto;
import ca.ulaval.glo2003.domain.dtos.ReservationTimeDto;
import ca.ulaval.glo2003.domain.dtos.RestaurantDto;

public class ReservationResponse {
    public String number;
    public String date;
    public ReservationTimeDto time;
    public Integer groupSize;
    public CustomerDto customer;
    public RestaurantDto restaurant;
}
