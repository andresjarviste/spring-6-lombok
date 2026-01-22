package guru.springframework.restmvc.controller;

import org.springframework.web.bind.annotation.RestController;

import guru.springframework.restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import guru.springframework.restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;



    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeerById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.updateBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = BEER_PATH, method = RequestMethod.GET)
    public List<Beer> listBeers() {
        log.debug("List Beers in controller was called");
        return beerService.listBeers();
    }

    @RequestMapping(value = BEER_PATH_ID, method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer Id in controller was called");
        return beerService.getBeerById(beerId);
    }
}
