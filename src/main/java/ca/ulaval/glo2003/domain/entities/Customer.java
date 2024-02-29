package ca.ulaval.glo2003.domain.entities;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String name;
    private String email;
    private String phoneNumber;

    public Customer(String name, String email, String phoneNumber) {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    private void setName(String name) {
        if (name == null) throw new NullPointerException("Name must be provided");
        try {
            this.name = name;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Name format is invalid");
        }
    }

    private void setEmail(String email) {
        if (email == null) throw new NullPointerException("Email must be provided");
        String regexPattern = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        if(Pattern.compile(regexPattern).matcher(email).matches())
        {
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email format is not valid(x@y.z)");
        }
    }

    private void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) throw new NullPointerException("Phone number must be provided");
        if(phoneNumber.matches("\\d{10}")) {
            this.phoneNumber = phoneNumber;
        }
        else {
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
