package guru.springframework.restmvc.services;
import java.util.UUID;

import guru.springframework.restmvc.model.Beer;

public interface BeerService {
    Beer getBeerById(UUID id);
}
