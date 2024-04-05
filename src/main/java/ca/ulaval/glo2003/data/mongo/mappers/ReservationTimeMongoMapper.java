package ca.ulaval.glo2003.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.ReservationTimeMongo;
import ca.ulaval.glo2003.domain.entities.ReservationTime;
import java.time.Duration;
import java.time.LocalTime;

public class ReservationTimeMongoMapper {

    public ReservationTimeMongo toMongo(ReservationTime reservationTime) {
        String start = reservationTime.getStart().toString();
        String end = reservationTime.getEnd().toString();
        return new ReservationTimeMongo(start, end);
    }

    public ReservationTime fromMongo(ReservationTimeMongo reservationTime) {
        LocalTime start = LocalTime.parse(reservationTime.start);
        LocalTime end = LocalTime.parse(reservationTime.end);
        Duration duration = Duration.between(start, end);
        int durationMinutes = (int) duration.toMinutes();
        return new ReservationTime(start, durationMinutes);
    }
}
