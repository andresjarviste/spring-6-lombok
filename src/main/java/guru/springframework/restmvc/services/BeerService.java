package guru.springframework.restmvc.services;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import guru.springframework.restmvc.model.Beer;

public interface BeerService {
    List<Beer> listBeers();
    Optional<Beer> getBeerById(UUID id);
    Beer saveNewBeer(Beer beer);
    void updateBeerById(UUID beerId, Beer beer);
    void deleteBeerById(UUID beerId);
    void patchBeerById(UUID beerId, Beer beer);
}
