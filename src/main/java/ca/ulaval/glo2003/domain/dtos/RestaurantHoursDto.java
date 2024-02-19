package ca.ulaval.glo2003.domain.dto;

import java.util.Objects;

public class RestaurantHoursDto {
    public String open;
    public String close;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RestaurantHoursDto) obj;
        return Objects.equals(this.open, that.open) && Objects.equals(this.close, that.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, close);
    }
}
