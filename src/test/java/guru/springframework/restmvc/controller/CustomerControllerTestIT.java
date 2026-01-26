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
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

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

    
    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
            .customerName("New Customer")
            .build();
        ResponseEntity responseEntity = customerController.handlePost(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        assertThat(locationUUID.length).isEqualTo(5);
    }

    
    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomerNotFound() {
        CustomerDTO customerDTO = CustomerDTO.builder()
            .customerName("New Customer")
            .build();
        assertThrows(NotFoundException.class, () -> {
            customerController.updateById(UUID.randomUUID(), customerDTO);
        });
    }


    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        final String newCustomerName = "New Customer Name";
        customerDTO.setCustomerName(newCustomerName);
        
        ResponseEntity responseEntity = customerController.updateById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(newCustomerName);
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteById(UUID.randomUUID());
        });
    }

    @Rollback  
    @Transactional
    @Test
    void testDeleteById() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

}
