package ca.ulaval.glo2003;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class RestaurantRequest {
    public String name;
    public Integer capacity;
    public HoursRequest hours;

    public RestaurantRequest() {}
}
