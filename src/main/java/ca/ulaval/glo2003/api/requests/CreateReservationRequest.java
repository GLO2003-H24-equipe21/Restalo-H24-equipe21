package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.domain.dto.CustomerDto;

public class CreateReservationRequest {
    public String date;
    public String startTime;
    public Integer groupSize;
    public CustomerDto customer;
}
