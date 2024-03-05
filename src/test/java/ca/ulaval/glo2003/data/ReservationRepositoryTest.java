package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.ReservationFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class ReservationRepositoryTest {
    ReservationRepository repository;

    Reservation reservation;
    String reservationNumber;

    @BeforeEach
    void setup() {
        reservation = new ReservationFixture().create();
        reservationNumber = reservation.getNumber();

        repository = new ReservationRepository();
    }

    @Test
    public void givenNonEmptyRepository_whenGet_thenReturnsReservation() {
        repository.add(reservation);

        Reservation gottenReservation = repository.get(reservationNumber);

        assertThat(gottenReservation).isEqualTo(reservation);
    }

    @Test
    public void givenEmptyRepository_whenGet_thenReturnsNull() {
        Reservation gottenReservation = repository.get(reservationNumber);

        assertThat(gottenReservation).isEqualTo(null);
    }
}