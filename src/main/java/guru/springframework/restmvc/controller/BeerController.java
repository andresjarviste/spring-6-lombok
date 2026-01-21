package guru.springframework.restmvc.controller;

import org.springframework.web.bind.annotation.RestController;

import guru.springframework.restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import guru.springframework.restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        log.debug("List Beers in controller was called");
        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get Beer Id in controller was called");
        return beerService.getBeerById(beerId);
    }
}
