package guru.springframework.restmvc.services;
import java.util.UUID;
import java.util.List;
import guru.springframework.restmvc.model.CustomerDTO;
import java.util.Optional;

public interface CustomerService {
    Optional<CustomerDTO> getCustomerById(UUID id);
    List<CustomerDTO> listCustomers();
    CustomerDTO saveNewCustomer(CustomerDTO customer);
    void updateCustomerById(UUID customerId, CustomerDTO customer);
    void deleteCustomerById(UUID customerId);
    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
