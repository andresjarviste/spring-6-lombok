package guru.springframework.restmvc.services;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import guru.springframework.restmvc.model.CustomerDTO;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {

        UUID randomUUID1 = UUID.randomUUID();
        UUID randomUUID2 = UUID.randomUUID();

        this.customerMap = new HashMap<>();
    
        customerMap.put(
            randomUUID1,
            CustomerDTO.builder()
                .id(randomUUID1)
                .customerName("John Doe")
                .version(1)
                .createdDate(java.time.LocalDateTime.now())
                .updateDate(java.time.LocalDateTime.now())
                .build()
        );
        customerMap.put(
            randomUUID2,
            CustomerDTO.builder()
                .id(randomUUID2)
                .customerName("Jane Smith")
                .version(1)
                .createdDate(java.time.LocalDateTime.now())
                .updateDate(java.time.LocalDateTime.now())
                .build()
        );

    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName(customer.getCustomerName())
                .createdDate(java.time.LocalDateTime.now())
                .updateDate(java.time.LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setUpdateDate(java.time.LocalDateTime.now());
        customerMap.put(customerId, existingCustomer);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        if (customer.getCustomerName() != null) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }
        existingCustomer.setUpdateDate(java.time.LocalDateTime.now());
        //customerMap.put(customerId, existingCustomer);
    }
}