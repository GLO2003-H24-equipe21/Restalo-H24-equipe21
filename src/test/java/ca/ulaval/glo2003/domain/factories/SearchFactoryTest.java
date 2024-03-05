package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.entities.Search;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchFactoryTest {
    private static final String VALID_NAME = "My restaurant";
    private static final String VALID_OPENED_FROM = "10:00:00";
    private static final String VALID_OPENED_TO = "20:00:00";

    private static final String INVALID_OPENED_FROM = "10 o clock";
    private static final String INVALID_OPENED_TO = "invalid hour";

    SearchFactory searchFactory;

    @BeforeEach
    void setup() {
        searchFactory = new SearchFactory();
    }

    @Test
    void givenValidInputs_whenCreate_thenSearchCreated() {
        Search search = searchFactory.create(VALID_NAME, VALID_OPENED_FROM, VALID_OPENED_TO);

        assertThat(search.getName()).isEqualTo(VALID_NAME);
        assertThat(search.getSearchOpened().getFrom())
                .isEqualTo(LocalTime.parse(VALID_OPENED_FROM));
        assertThat(search.getSearchOpened().getTo()).isEqualTo(LocalTime.parse(VALID_OPENED_TO));
    }

    @Test
    void givenNullName_whenCreate_thenNameIsNull() {
        Search search = searchFactory.create(null, VALID_OPENED_FROM, VALID_OPENED_TO);

        assertThat(search.getName()).isEqualTo(null);
    }

    @Test
    void givenNullOpenedFrom_whenCreate_thenOpenedFromIsNull() {
        Search search = searchFactory.create(VALID_NAME, null, VALID_OPENED_TO);

        assertThat(search.getSearchOpened().getFrom()).isEqualTo(null);
    }

    @Test
    void givenNullOpenedTo_whenCreate_thenOpenedFromIsNull() {
        Search search = searchFactory.create(VALID_NAME, VALID_OPENED_FROM, null);

        assertThat(search.getSearchOpened().getTo()).isEqualTo(null);
    }

    @Test
    void givenInvalidOpenedFromFormat_whenCreate_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchFactory.create(VALID_NAME, INVALID_OPENED_FROM, VALID_OPENED_TO));
    }

    @Test
    void givenInvalidOpenedToFormat_whenCreate_thenThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> searchFactory.create(VALID_NAME, VALID_OPENED_FROM, INVALID_OPENED_TO));
    }
}
