package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.data.DatastoreProvider;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.ReservationRepositoryTest;
import ca.ulaval.glo2003.domain.entities.*;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class ReservationRepositoryMongoTest extends ReservationRepositoryTest {

    @Container
    private final MongoDBContainer mongoContainer = new MongoDBContainer("mongo:7.0");

    @Override
    protected ReservationRepository createRepository() {
        var mongoUrl = MongoClients.create(mongoContainer.getConnectionString());
        var datastore = Morphia.createDatastore(mongoUrl, "tests");
        return new ReservationRepositoryMongo(datastore);
    }
}