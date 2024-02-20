package ca.ulaval.glo2003.domain.entities;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final LocalTime start;
    private final LocalTime end;

    public ReservationTime(LocalTime start, LocalTime end) {
        this.start = roundToNext15Minutes(start);
        this.end = roundToNext15Minutes(end);
    }

    public ReservationTime(LocalTime start, int durationInMinutes) {

        this.start = roundToNext15Minutes(start);
        this.end = this.start.plusMinutes(durationInMinutes);
    }

    private LocalTime roundToNext15Minutes(LocalTime time) {
        return time.withSecond(0)
                .withNano(0)
                .plusMinutes((75 - time.getMinute()) % 15);
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
