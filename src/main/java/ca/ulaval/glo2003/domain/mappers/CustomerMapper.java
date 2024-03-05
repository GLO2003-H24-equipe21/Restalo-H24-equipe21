package ca.ulaval.glo2003.domain.mappers;

import ca.ulaval.glo2003.domain.dto.CustomerDto;
import ca.ulaval.glo2003.domain.entities.Customer;
import java.util.Objects;

public class CustomerMapper {

    public CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.name = customer.getName();
        dto.email = customer.getEmail();
        dto.phoneNumber = customer.getPhoneNumber();
        return dto;
    }
}
