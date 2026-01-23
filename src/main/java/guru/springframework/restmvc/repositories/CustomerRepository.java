package guru.springframework.restmvc.repositories;

import java.util.UUID;
import guru.springframework.restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    
}
