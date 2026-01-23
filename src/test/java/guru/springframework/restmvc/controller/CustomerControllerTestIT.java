package guru.springframework.restmvc.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import guru.springframework.restmvc.repositories.CustomerRepository;
import java.util.List;
import java.util.UUID;

import guru.springframework.restmvc.entities.Customer;
import guru.springframework.restmvc.model.CustomerDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class CustomerControllerTestIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomers() {
        List<CustomerDTO> customers = customerController.listCustomers();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.listCustomers();
        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
}
