package guru.springframework.restmvc.services;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import guru.springframework.restmvc.model.BeerDTO;

public interface BeerService {
    List<BeerDTO> listBeers();
    Optional<BeerDTO> getBeerById(UUID id);
    BeerDTO saveNewBeer(BeerDTO beer);
    void updateBeerById(UUID beerId, BeerDTO beer);
    void deleteBeerById(UUID beerId);
    void patchBeerById(UUID beerId, BeerDTO beer);
}
