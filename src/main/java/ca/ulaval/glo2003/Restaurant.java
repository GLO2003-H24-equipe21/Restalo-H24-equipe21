package ca.ulaval.glo2003;

import java.time.LocalTime;

public class Restaurant {
    private String id;
    private String name;
    private int capacity;
    private String owner;
    private String openingTime;
    private String closingTime;

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
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

    private boolean validateRestaurantData(Restaurant restaurant){
        if (restaurant.getName() == null || restaurant.getName().isEmpty())
            return false;

        if (restaurant.getCapacity() < 1)
            return false;

        if (restaurant.getOwner() == null || restaurant.getOwner().isEmpty())
            return false;

        if (!validateOpeningAndClosingTimes(restaurant.getOpeningTime(), restaurant.getClosingTime())) {
            return false;
        }

        if (!validateUniqueId(restaurant.getId())) {
            return false;
        }

        return true;
    }

    private boolean validateOpeningAndClosingTimes(String openingTime, String closingTime) {
        LocalTime open = LocalTime.parse(openingTime);
        LocalTime close = LocalTime.parse(closingTime);

        return !open.isBefore(LocalTime.MIDNIGHT) &&
                close.isBefore(LocalTime.MIDNIGHT) &&
                close.isAfter(open.plusHours(1));
    }

    private boolean validateUniqueId(String id) {
        // À implémenter
        return true;
    }
}
