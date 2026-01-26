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
import java.util.concurrent.atomic.AtomicReference;


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
        return customerMapper.customerToCustomerDTO(
            customerRepository.save(customerMapper.customerDTOToCustomer(customer))
        );
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(customerToSave -> {
            customerToSave.setCustomerName(customer.getCustomerName());
            atomicReference.set(
               Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(customerToSave)))
            );
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
