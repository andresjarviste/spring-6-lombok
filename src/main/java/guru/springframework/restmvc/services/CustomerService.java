package guru.springframework.restmvc.services;
import java.util.UUID;
import java.util.List;
import guru.springframework.restmvc.model.Customer;

public interface CustomerService {
    Customer getCustomerById(UUID id);
    List<Customer> listCustomers();
}
