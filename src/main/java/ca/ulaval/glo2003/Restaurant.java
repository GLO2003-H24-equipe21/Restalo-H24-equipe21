package ca.ulaval.glo2003;

import java.util.UUID;

public class Restaurant {

    private final UUID id = UUID.randomUUID();
    private String name;
    private Integer capacity;
    private Hours hours;

    private Reservation reservation;

    public Restaurant(String name, Integer capacity, String open, String close, int duration) {
        setName(name);
        setCapacity(capacity);
        setHours(open, close);
        setReservation(duration);
    }

    public Restaurant(String name, Integer capacity, String open, String close) {
        setName(name);
        setCapacity(capacity);
        setHours(open, close);
    }

    public void setName(String name) {
        if (name == null) throw new NullPointerException("Name must be provided");
        if (name.isEmpty()) throw new IllegalArgumentException("Name must not be empty");
        this.name = name;
    }

    public void setCapacity(Integer capacity) {
        if (capacity == null) throw new NullPointerException("Capacity must be provided");
        if (capacity < 1) throw new IllegalArgumentException("Minimal capacity must be one");
        this.capacity = capacity;
    }

    public void setHours(String open, String close) {
        this.hours = new Hours(open, close);
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Hours getHours() {
        return hours;
    }

    public String getId() {
        return id.toString();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(int duration) {
        this.reservation = new Reservation();
        this.reservation.setDuration(duration);
    }
}
