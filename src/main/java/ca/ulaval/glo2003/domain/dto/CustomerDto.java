package ca.ulaval.glo2003.domain.dto;

import jakarta.validation.constraints.NotNull;

public class CustomerDto {
    @NotNull(message = "Customer name must be provided") public String name;

    @NotNull(message = "Customer email must be provided") public String email;

    @NotNull(message = "Customer phone number must be provided") public String phoneNumber;
}