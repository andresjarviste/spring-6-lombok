package guru.springframework.restmvc.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import guru.springframework.restmvc.repositories.BeerRepository;
import java.util.List;
import java.util.UUID;

import guru.springframework.restmvc.entities.Beer;
import guru.springframework.restmvc.model.BeerDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import guru.springframework.restmvc.model.BeerStyle;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import guru.springframework.restmvc.mappers.BeerMapper;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class BeerControllerTestIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;


    @Rollback
    @Transactional
    @Test
    void testDeleteBeer() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(beer.getId())).isEmpty();

    }
    
    @Test
    void testUpdateBeerNotFound() {
        BeerDTO beerDTO = BeerDTO.builder()
            .beerName("New Beer")
            .build();
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), beerDTO);
        });
    }
    

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteById(UUID.randomUUID());
        });
    }
    
    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String newBeerName = "New Beer Name";
        beerDTO.setBeerName(newBeerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(newBeerName);
    }

    @Test
    void testListBeers() {
        List<BeerDTO> beers = beerController.listBeers();
        assertThat(beers.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beers = beerController.listBeers();
        assertThat(beers.size()).isEqualTo(0);
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
            .beerName("New Beer")
            .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        System.out.println(responseEntity.getHeaders().getLocation().getPath());
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        assertThat(locationUUID.length).isEqualTo(5);
        assertThat(locationUUID[2]).isNotNull();
        String savedUUID = locationUUID[4];
        System.out.println(savedUUID);
        assertThat(savedUUID).isNotNull();
        BeerDTO savedBeer = beerController.getBeerById(UUID.fromString(savedUUID));
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getBeerName()).isEqualTo(beerDTO.getBeerName());
    }
}
