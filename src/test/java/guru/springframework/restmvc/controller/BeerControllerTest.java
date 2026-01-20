package guru.springframework.restmvc.controller;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BeerControllerTest {

    @Autowired
    BeerController beerController;

    @Test
    void testGetBeerById() {
        System.out.println(beerController.getBeerById(UUID.randomUUID()));
    }
}
