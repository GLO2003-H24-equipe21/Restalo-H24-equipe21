package ca.ulaval.glo2003.data.mongo.entities;

public class CustomerMongo {
    public String name;
    public String email;
    public String phoneNumber;

    public CustomerMongo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
