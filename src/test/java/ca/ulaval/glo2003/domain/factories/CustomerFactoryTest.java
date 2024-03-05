package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerFactoryTest {
    private static final String VALID_NAME = "Johnny Cash";
    private static final String VALID_EMAIL = "johnny.cash@example.com";
    private static final String VALID_PHONE_NUMBER = "1234567890";

    private static final String EMPTY_NAME = "";
    private static final String INVALID_EMAIL = "invalid_email";
    private static final String INVALID_PHONE_NUMBER = "123";

    CustomerFactory customerFactory;

    @BeforeEach
    void setUp() {
        customerFactory = new CustomerFactory();
    }

    @Test
    void givenValidInputs_whenCreate_thenCustomerCreated() {
        Customer customer = new Customer(VALID_NAME, VALID_EMAIL, VALID_PHONE_NUMBER);

        Customer gottenCustomer =
                customerFactory.create(VALID_NAME, VALID_EMAIL, VALID_PHONE_NUMBER);

        assertThat(gottenCustomer).isEqualTo(customer);
    }

    @Test
    void givenEmptyName_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerFactory.create(EMPTY_NAME, VALID_EMAIL, VALID_PHONE_NUMBER));
    }

    @Test
    void givenInvalidEmail_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerFactory.create(VALID_NAME, INVALID_EMAIL, VALID_PHONE_NUMBER));
    }

    @Test
    void givenInvalidPhoneNumber_whenCreate_throwsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> customerFactory.create(VALID_NAME, VALID_EMAIL, INVALID_PHONE_NUMBER));
    }
}
