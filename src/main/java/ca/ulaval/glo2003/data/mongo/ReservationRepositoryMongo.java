package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.entities.Reservation;
import dev.morphia.Datastore;
import java.util.List;
import java.util.Optional;

public class ReservationRepositoryMongo implements ReservationRepository {
    private final Datastore datastore;

    public ReservationRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void add(Reservation reservation) {}

    @Override
    public Optional<Reservation> get(String reservationId) {
        return Optional.empty();
    }

    @Override
    public void delete(String reservationId) {}

    @Override
    public List<Reservation> searchReservations(
            String restaurantId, String ownerId, String date, String customerName) {
        return null;
    }
}
