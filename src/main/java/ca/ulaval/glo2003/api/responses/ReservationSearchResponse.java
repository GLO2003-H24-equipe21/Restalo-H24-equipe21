package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.api.pojos.ReservationTimePojo;

public record ReservationSearchResponse(
        String number,
        String date,
        ReservationTimePojo time,
        Integer groupSize,
        CustomerPojo customer) {}
