package ca.ulaval.glo2003.domain.dtos;

import java.util.Objects;

public class ReservationTimeDto {
    public String start;
    public String end;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ReservationTimeDto) obj;
        return Objects.equals(this.start, that.start) && Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
