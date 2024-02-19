package ca.ulaval.glo2003;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class ReservationRequest {
    public String date;
    public String startTime;
    public Integer groupSize;
    public Customer customer;

    public ReservationRequest() {}
}
