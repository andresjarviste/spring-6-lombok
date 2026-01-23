package guru.springframework.restmvc.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import guru.springframework.restmvc.entities.Beer;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer beer = Beer.builder()
            .beerName("Test Beer")
            .build();
        Beer savedBeer = beerRepository.save(beer);
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}