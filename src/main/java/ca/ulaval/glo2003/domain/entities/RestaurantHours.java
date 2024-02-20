package ca.ulaval.glo2003.domain.entities;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

public class RestaurantHours {
    private final LocalTime open;
    private final LocalTime close;

    public RestaurantHours(LocalTime open, LocalTime close) {
        validateHours(open, close);
        this.open = open;
        this.close = close;
    }

    private void validateHours(LocalTime open, LocalTime close) {
        if (close.isBefore(open)) throw new IllegalArgumentException("Incoherent opening hours");
        if (Duration.between(open, close).toHours() < 1) {
            throw new IllegalArgumentException("Restaurant must be open at least 1 hour");
        }
    }

    public LocalTime getOpen() {
        return open;
    }

    public LocalTime getClose() {
        return close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantHours that = (RestaurantHours) o;
        return Objects.equals(open, that.open) && Objects.equals(close, that.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, close);
    }
}
