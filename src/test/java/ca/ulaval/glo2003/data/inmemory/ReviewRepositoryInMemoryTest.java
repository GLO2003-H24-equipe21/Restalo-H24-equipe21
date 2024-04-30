package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReviewRepository;
import ca.ulaval.glo2003.domain.ReviewRepositoryTest;

public class ReviewRepositoryInMemoryTest extends ReviewRepositoryTest {

    @Override
    protected ReviewRepository createRepository() {
        return new ReviewRepositoryInMemory();
    }
}
