package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class CreateRestaurantRequest {
    @NotNull(message = "Restaurant name must be provided.") public String name;

    @NotNull(message = "Restaurant capacity must be provided") public Integer capacity;

    @Valid
    @NotNull(message = "Restaurant hours must be provided") public RestaurantHoursDto hours;

    public RestaurantReservationsDto reservations;
}
