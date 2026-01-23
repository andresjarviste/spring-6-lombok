package guru.springframework.restmvc.services;

import guru.springframework.restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;

import guru.springframework.restmvc.repositories.CustomerRepository;
import guru.springframework.restmvc.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(
            customerMapper.customerToCustomerDTO(
                customerRepository.findById(id).orElse(null)
            )
        );
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository
        .findAll()
        .stream()
        .map(customerMapper::customerToCustomerDTO)
        .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
