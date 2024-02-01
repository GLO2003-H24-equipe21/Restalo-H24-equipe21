package ca.ulaval.glo2003;

import java.time.LocalTime;
import java.util.UUID;

public class Restaurant {
    private final UUID id = UUID.randomUUID();
    private String name;
    private int capacity;
    private String owner;
    private String openingTime;
    private String closingTime;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getOwner() {
        return owner;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    private boolean isRestaurantDataValid(){
        return isNameValid() &&
                isCapacityValid() &&
                isOwnerValid() &&
                isOpeningTimeValid() &&
                isClosingTimeValid() &&
                isOpenAtLeastOneHour();
    }

    private boolean isNameValid() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Restaurant's name is invalid.");
        }
        return true;
    }

    private boolean isCapacityValid() {
        if (capacity < 1) {
            throw new IllegalArgumentException("Restaurant's capacity is invalid.");
        }
        return true;
    }

    private boolean isOwnerValid() {
        if (owner == null || owner.isEmpty()){
            throw new IllegalArgumentException("Restaurant's owner is invalid.");
        }
        return true;
    }

    private boolean isOpeningTimeValid() {
        if (LocalTime.parse(openingTime).isBefore(LocalTime.MIDNIGHT)){
            throw new IllegalArgumentException("Restaurant's opening time is invalid.");
        }
        return true;
    }

    private boolean isClosingTimeValid() {
        if (LocalTime.parse(openingTime).isAfter(LocalTime.MIDNIGHT)){
            throw new IllegalArgumentException("Restaurant's closing time is invalid.");
        }
        return true;
    }

    private boolean isOpenAtLeastOneHour() {
        if (LocalTime.parse(closingTime).isBefore(LocalTime.MIDNIGHT) &&
                LocalTime.parse(closingTime).isAfter(LocalTime.parse(openingTime).plusHours(1))){
            throw new IllegalArgumentException("The restaurant is not open for at least 1 hour.");
        }
        return true;
    }
}