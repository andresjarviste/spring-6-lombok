package guru.springframework.restmvc.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import guru.springframework.restmvc.entities.Customer;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = Customer.builder()
            .customerName("Test Customer")
            .build();
        Customer savedCustomer = customerRepository.save(customer);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }
}