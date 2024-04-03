package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class AvailabilitiesFixture {
    private Integer capacity = 12;
    private LocalDate date = LocalDate.now().plusDays(2);
    private LocalTime from = LocalTime.of(10, 0, 0);
    private LocalTime to = LocalTime.of(23, 30, 0);

    public Map<LocalDateTime, Integer> create() {
        Map<LocalDateTime, Integer> availabilities = new LinkedHashMap<>();
        for (LocalTime current = from;
                current.isBefore(to.plusSeconds(1));
                current = current.plusMinutes(15)) {
            availabilities.put(LocalDateTime.of(date, current), capacity);
        }
        return availabilities;
    }

    public AvailabilitiesFixture withCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public AvailabilitiesFixture on(LocalDate date) {
        this.date = date;
        return this;
    }

    public AvailabilitiesFixture from(LocalTime from) {
        this.from = from;
        return this;
    }

    public AvailabilitiesFixture to(LocalTime to) {
        this.to = to;
        return this;
    }
}
