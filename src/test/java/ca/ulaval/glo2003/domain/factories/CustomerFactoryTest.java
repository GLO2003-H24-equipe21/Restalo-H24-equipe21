package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Customer;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CustomerFactoryTest {

    private final CustomerFactory factory = new CustomerFactory();

    @Test
    void create_shouldCreateCustomer_whenAllInputsAreValid() {
        String name = "Johnny Cash";
        String email = "johnny.cash@example.com";
        String phoneNumber = "1234567890";

        Customer customer = factory.create(name, email, phoneNumber);

        assertThat(customer.getName()).isEqualTo(name);
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenNameIsEmpty() {
        String name = "";
        String email = "johnny.cash@example.com";
        String phoneNumber = "1234567890";

        assertThatThrownBy(() -> factory.create(name, email, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name must not be empty");
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenEmailIsInvalid() {
        String name = "Johnny Cash";
        String email = "invalid_email";
        String phoneNumber = "1234567890";

        assertThatThrownBy(() -> factory.create(name, email, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email must be valid");
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenPhoneNumberIsInvalid() {
        String name = "Johnny Cash";
        String email = "johnny.cash@example.com";
        String phoneNumber = "123";

        assertThatThrownBy(() -> factory.create(name, email, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("phone number must be 10 numbers");
    }
}