package ca.ulaval.glo2003.domain.dto;

import java.util.Objects;

public class RestaurantDto {
    public String id;
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantReservationsDto reservations;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RestaurantDto) obj;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.capacity, that.capacity)
                && Objects.equals(this.hours, that.hours)
                && Objects.equals(this.reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, hours, reservations);
    }
}
