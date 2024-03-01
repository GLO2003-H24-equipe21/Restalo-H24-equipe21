package ca.ulaval.glo2003.domain.dto;

import jakarta.validation.constraints.NotNull;

public class RestaurantHoursDto {
    @NotNull(message = "Restaurant opening time must be provided") public String open;

    @NotNull(message = "Restaurant closing time must be provided") public String close;
}