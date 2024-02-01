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
        return name != null && !name.isEmpty();
    }

    private boolean isCapacityValid() {
        return capacity >= 1;
    }

    private boolean isOwnerValid() {
        return owner != null && !owner.isEmpty();
    }

    private boolean isOpeningTimeValid() {
        return !LocalTime.parse(openingTime).isBefore(LocalTime.MIDNIGHT);
    }

    private boolean isClosingTimeValid() {
        return !LocalTime.parse(openingTime).isAfter(LocalTime.MIDNIGHT);
    }

    private boolean isOpenAtLeastOneHour() {
        return LocalTime.parse(closingTime).isBefore(LocalTime.MIDNIGHT) &&
                LocalTime.parse(closingTime).isAfter(LocalTime.parse(openingTime).plusHours(1));
    }

    // LocalTime open = LocalTime.parse(openingTime);
    // LocalTime close = LocalTime.parse(closingTime);
}