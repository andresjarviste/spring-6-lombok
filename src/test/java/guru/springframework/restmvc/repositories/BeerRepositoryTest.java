package guru.springframework.restmvc.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import guru.springframework.restmvc.entities.Beer;
import jakarta.validation.ConstraintViolationException;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong() {
        Beer beer = Beer.builder()
            .beerName("Test Beer Name That Is Too Long And Should Fail Validation ölkjölkj ölj ölj ölj ölk jölj")
            .upc("1234567890")
            .price(new BigDecimal("10.99"))
            .quantityOnHand(100)
            .build();

            assertThrows(ConstraintViolationException.class, () -> {
                Beer savedBeer = beerRepository.save(beer);
                beerRepository.flush();
            });
    }
    @Test
    void testSaveBeer() {
        Beer beer = Beer.builder()
            .beerName("Test Beer")
            .upc("1234567890")
            .price(new BigDecimal("10.99"))
            .quantityOnHand(100)
            .build();
        Beer savedBeer = beerRepository.save(beer);

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}