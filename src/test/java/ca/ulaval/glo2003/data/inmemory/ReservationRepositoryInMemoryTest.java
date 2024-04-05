package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.ReservationRepositoryTest;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.ReservationFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReservationRepositoryInMemoryTest extends ReservationRepositoryTest {
    @Override
    protected ReservationRepository createRepository() {
        return new ReservationRepositoryInMemory();
    }
}