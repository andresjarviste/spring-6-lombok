package guru.springframework.restmvc.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.restmvc.repositories.BeerRepository;
import guru.springframework.restmvc.repositories.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

@DataJpaTest
class BootstrapDataTest {
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testLoadBootstrapData() throws Exception {
        BootstrapData bootstrapData = new BootstrapData(beerRepository, customerRepository);
        bootstrapData.run();
        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(2);
    }

}