package ca.ulaval.glo2003.domain.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationTimeTest {
    private static final String START = "13:01:00";

    private static final String SECONDS_START = "13:00:01";

    private static final String NANO_START = "13:00:00.01";
    private static final Integer DURATION = 60;

    private static final String ROUNDED_START = "13:15:00";

    private static final String ROUNDED_END = "14:15:00";

    @BeforeEach
    void setUp() {}

    @Test
    void givenValidNormalInputs_thenReservationTimeCreatedProperly() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.parse(START), DURATION);

        Assertions.assertThat(reservationTime.getStart()).isEqualTo(ROUNDED_START);
        Assertions.assertThat(reservationTime.getEnd()).isEqualTo(ROUNDED_END);
    }

    @Test
    void givenValidSecondsInputs_thenReservationTimeCreatedProperly() {
        ReservationTime reservationTime =
                new ReservationTime(LocalTime.parse(SECONDS_START), DURATION);

        Assertions.assertThat(reservationTime.getStart()).isEqualTo(ROUNDED_START);
        Assertions.assertThat(reservationTime.getEnd()).isEqualTo(ROUNDED_END);
    }

    @Test
    void givenValidNanoInputs_thenReservationTimeCreatedProperly() {
        ReservationTime reservationTime =
                new ReservationTime(LocalTime.parse(NANO_START), DURATION);

        Assertions.assertThat(reservationTime.getStart()).isEqualTo(ROUNDED_START);
        Assertions.assertThat(reservationTime.getEnd()).isEqualTo(ROUNDED_END);
    }
}
