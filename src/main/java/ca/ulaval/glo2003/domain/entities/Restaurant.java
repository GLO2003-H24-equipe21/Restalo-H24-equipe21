package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Restaurant {
    private final String ownerId;
    private final String id;
    private final String name;
    private final Integer capacity;
    private final RestaurantHours hours;
    private final RestaurantConfiguration configuration;
    private final Map<String, Integer> availabilities;

    public Restaurant(
            String id,
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHours hours,
            RestaurantConfiguration configuration,
            Map<String, Integer> availabilities) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.configuration = configuration;
        this.availabilities = availabilities;
    }

    public void addReservation(LocalDate date, ReservationTime time, Integer groupSize) {
        List<String> intervals = create15MinutesIntervals(date, time.getStart(), time.getEnd());
        checkAvailabilities(intervals, groupSize);
        for (String dateTime: intervals) {
            availabilities.put(dateTime, availabilities.getOrDefault(dateTime, capacity) - groupSize);
        }
    }

    private List<String> create15MinutesIntervals(LocalDate date, LocalTime start, LocalTime end) {
        List<String> localDateTimes = new ArrayList<>();
        for (LocalTime current = start; current.isBefore(end); current = current.plusMinutes(15)) {
            localDateTimes.add(LocalDateTime.of(date, current).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        return localDateTimes;
    }

    private void checkAvailabilities(List<String> intervals, Integer groupSize) {
        for (String dateTime: intervals) {
            if (availabilities.getOrDefault(dateTime, capacity) < groupSize) {
                throw new RuntimeException("No availabilities at " + dateTime);
            }
        }
    }

    public Map<String, Integer> getAvailabilities(LocalDate date) {
        LocalTime roundedOpen = roundToNext15Minutes(hours.getOpen());
        LocalTime roundedCloseMinusDuration = roundToNext15Minutes(hours.getClose().minusMinutes(configuration.getDuration()));
        List<String> intervals = create15MinutesIntervals(date, roundedOpen, roundedCloseMinusDuration);
        Map<String, Integer> dateAvailabilities = new LinkedHashMap<>();

        for (String dateTime: intervals) {
            dateAvailabilities.put(dateTime, availabilities.getOrDefault(dateTime, capacity));
        }
        return dateAvailabilities;
    }

    private LocalTime roundToNext15Minutes(LocalTime time) {
        if (time.getNano() != 0) {
            time = time.plusSeconds(1);
        }
        return time.withNano(0).plusSeconds((4500 - (time.toSecondOfDay() % 3600)) % 900);
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public RestaurantHours getHours() {
        return hours;
    }

    public RestaurantConfiguration getConfiguration() {
        return configuration;
    }

    public Map<String, Integer> getAvailabilities() {
        return availabilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(ownerId, that.ownerId) && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(capacity, that.capacity) && Objects.equals(hours, that.hours) && Objects.equals(configuration, that.configuration) && Objects.equals(availabilities, that.availabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, id, name, capacity, hours, configuration, availabilities);
    }
}
