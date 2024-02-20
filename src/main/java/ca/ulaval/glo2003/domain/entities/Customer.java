package ca.ulaval.glo2003.domain.entities;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private final String name;
    private final String email;
    private final String phoneNumber;

    public Customer(String name, String email, String phoneNumber) {
        validateName(name);
        validateEmail(email);
        validatePhoneNumber(phoneNumber);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Customer name must not be empty");
        }
    }

    private void validateEmail(String email) {
        if (!Pattern.compile("[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+").matcher(email).matches()) {
            throw new IllegalArgumentException("Customer email must be valid (x@y.z)");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!Pattern.compile("\\d{18}").matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Customer phone number must be 10 numbers");
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name)
                && Objects.equals(email, customer.email)
                && Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber);
    }
}
