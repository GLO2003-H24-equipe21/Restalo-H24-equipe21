package ca.ulaval.glo2003.domain.factories;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.entities.Search;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchFactoryTest {

    SearchFactory searchFactory;

    @BeforeEach
    void setup() {
        searchFactory = new SearchFactory();
    }

    @Test
    void givenValidInputs_whenCreate_thenSearchCreated() {
        String name = "My restaurant";
        String openedFrom = "10:00:00";
        String openedTo = "20:00:00";

        Search search = searchFactory.create(name, openedFrom, openedTo);

        assertThat(search.getName()).isEqualTo(name);
        assertThat(search.getSearchOpened().getFrom()).isEqualTo(LocalTime.of(10, 0, 0));
        assertThat(search.getSearchOpened().getTo()).isEqualTo(LocalTime.of(20, 0, 0));
    }

    @Test
    void givenNullName_whenCreate_thenNameIsNull() {
        String name = null;
        String openedFrom = "10:00:00";
        String openedTo = "20:00:00";

        Search search = searchFactory.create(name, openedFrom, openedTo);

        assertThat(search.getName()).isEqualTo(null);
    }

    @Test
    void givenNullOpenedFrom_whenCreate_thenOpenedFromIsNull() {
        String name = "Mc Donald's";
        String openedFrom = null;
        String openedTo = "20:00:00";

        Search search = searchFactory.create(name, openedFrom, openedTo);

        assertThat(search.getSearchOpened().getFrom()).isEqualTo(null);
    }

    @Test
    void givenNullOpenedTo_whenCreate_thenOpenedFromIsNull() {
        String name = "Mc Donald's";
        String openedFrom = "09:00:00";
        String openedTo = null;

        Search search = searchFactory.create(name, openedFrom, openedTo);

        assertThat(search.getSearchOpened().getTo()).isEqualTo(null);
    }

    @Test
    void givenInvalidOpenedFromFormat_whenCreate_thenThrowIllegalArgumentException() {
        String name = "A restaurant";
        String openedFrom = "9 hours";
        String openedTo = "20:00:00";

        assertThrows(
                IllegalArgumentException.class,
                () -> searchFactory.create(name, openedFrom, openedTo));
    }

    @Test
    void givenInvalidOpenedToFormat_whenCreate_thenThrowIllegalArgumentException() {
        String name = "A restaurant";
        String openedFrom = "10:00:00";
        String openedTo = "Invalid hour format";

        assertThrows(
                IllegalArgumentException.class,
                () -> searchFactory.create(name, openedFrom, openedTo));
    }
}
