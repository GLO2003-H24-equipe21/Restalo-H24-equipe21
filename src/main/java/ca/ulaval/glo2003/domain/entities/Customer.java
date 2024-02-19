package ca.ulaval.glo2003.domain.entities;

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

    public void setName(String name) {
        if (name == null) throw new NullPointerException("Name must be provided");
        try {
            this.name = name;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Name format is invalid");
        }
    }

    public void setEmail(String email) {
        if (email == null) throw new NullPointerException("Email must be provided");
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(Pattern.compile(regexPattern).matcher(email).matches())
        {
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email format is not valid(x@y.z)");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) throw new NullPointerException("Phone number must be provided");
        if(phoneNumber.matches("\\d+")) {
            this.phoneNumber = phoneNumber;
        }
        else {
            throw new IllegalArgumentException("Phone number format is not valid (########## (10 numbers))");
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
}
