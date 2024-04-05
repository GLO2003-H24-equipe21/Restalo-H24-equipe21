package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.RestaurantRepositoryTest;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryInMemoryTest extends RestaurantRepositoryTest {

    @Override
    protected RestaurantRepository createRepository() {
        return new RestaurantRepositoryInMemory();
    }
}