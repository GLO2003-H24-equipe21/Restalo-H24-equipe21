package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.CustomerDto;
import ca.ulaval.glo2003.domain.entities.Customer;
import java.util.Objects;

public class CustomerMapper {
    public Customer fromDto(CustomerDto dto) {
        Objects.requireNonNull(dto.name, "Customer name must be provided");
        Objects.requireNonNull(dto.email, "Customer email must be provided");
        Objects.requireNonNull(dto.phoneNumber, "Customer phone number must be provided");
        return new Customer(dto.name, dto.email, dto.phoneNumber);
    }

    public CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.name = customer.getName();
        dto.email = customer.getEmail();
        dto.phoneNumber = customer.getPhoneNumber();
        return dto;
    }
}
