package guru.springframework.restmvc.controller;

import org.springframework.stereotype.Controller;
import guru.springframework.restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import guru.springframework.restmvc.model.Beer;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerById(UUID beerId) {
        log.debug("Get Beer Id in controller was called");
        return beerService.getBeerById(beerId);
    }
}
