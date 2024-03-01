package ca.ulaval.glo2003.domain.dto;

import java.util.Objects;

public class RestaurantReservationsDto {
    public Integer duration;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RestaurantReservationsDto) obj;
        return Objects.equals(this.duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }
}
