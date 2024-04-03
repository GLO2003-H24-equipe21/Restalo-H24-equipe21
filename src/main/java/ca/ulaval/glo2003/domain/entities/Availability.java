package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDateTime;

public class Availability {
    private final LocalDateTime start;
    private final Integer remainingPlaces;

    public Availability(LocalDateTime start, Integer remainingPlaces) {
        this.start = start;
        this.remainingPlaces = remainingPlaces;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public Integer getRemainingPlaces() {
        return remainingPlaces;
    }
}
