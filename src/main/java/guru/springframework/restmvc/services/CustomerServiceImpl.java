package guru.springframework.restmvc.services;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import guru.springframework.restmvc.model.Customer;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {

        UUID randomUUID1 = UUID.randomUUID();
        UUID randomUUID2 = UUID.randomUUID();

        this.customerMap = Map.of(
                randomUUID1,
                Customer.builder()
                        .id(randomUUID1)
                        .customerName("John Doe")
                        .version(1)
                        .createdDate(java.time.LocalDateTime.now())
                        .updateDate(java.time.LocalDateTime.now())
                        .build(),
                randomUUID2,
                Customer.builder()
                        .id(randomUUID2)
                        .customerName("Jane Smith")
                        .version(1)
                        .createdDate(java.time.LocalDateTime.now())
                        .updateDate(java.time.LocalDateTime.now())
                        .build()
        );

    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }
}