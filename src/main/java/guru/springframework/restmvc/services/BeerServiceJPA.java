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
import guru.springframework.restmvc.entities.Beer;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.util.StringUtils;

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
        return beerMapper.beerToBeerDTO(
            beerRepository.save(beerMapper.beerDTOToBeer(beer))
        );
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(beerToSave -> {
            beerToSave.setBeerName(beer.getBeerName());
            beerToSave.setBeerStyle(beer.getBeerStyle());
            beerToSave.setUpc(beer.getUpc());
            beerToSave.setPrice(beer.getPrice());
            beerToSave.setQuantityOnHand(beer.getQuantityOnHand());
            atomicReference.set(Optional.of(beerMapper
                .beerToBeerDTO(beerRepository.save(beerToSave))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(beerToPatch -> {
            if (StringUtils.hasText(beer.getBeerName())){
                beerToPatch.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null){
                beerToPatch.setBeerStyle(beer.getBeerStyle());
            }
            if (StringUtils.hasText(beer.getUpc())){
                beerToPatch.setUpc(beer.getUpc());
            }
            if (beer.getPrice() != null){
                beerToPatch.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null){
                beerToPatch.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(
                Optional.of(beerMapper
                    .beerToBeerDTO(beerRepository.save(beerToPatch))
                )
            );
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
