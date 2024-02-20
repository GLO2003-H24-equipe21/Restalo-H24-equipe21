package ca.ulaval.glo2003.domain.dto;

import java.util.Objects;

public class ReservationDto {
    public String number;
    public String date;
    public ReservationTimeDto time;
    public Integer groupSize;
    public CustomerDto customer;
    public RestaurantDto restaurant;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ReservationDto) obj;
        return Objects.equals(this.number, that.number)
                && Objects.equals(this.date, that.date)
                && Objects.equals(this.time, that.time)
                && Objects.equals(this.groupSize, that.groupSize)
                && Objects.equals(this.customer, that.customer)
                && Objects.equals(this.restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, time, groupSize, customer, restaurant);
    }
}
