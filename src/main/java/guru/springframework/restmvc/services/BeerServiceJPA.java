package guru.springframework.restmvc.services;

import guru.springframework.restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import guru.springframework.restmvc.repositories.BeerRepository;
import guru.springframework.restmvc.mappers.BeerMapper;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository
            .findAll()
            .stream()
            .map(beerMapper::beerToBeerDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(
            beerMapper.beerToBeerDTO(
                beerRepository.findById(id).orElse(null)
            )
        );
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
