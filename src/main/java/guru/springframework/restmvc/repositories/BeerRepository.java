package guru.springframework.restmvc.repositories;

import java.util.UUID;
import guru.springframework.restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    
}
