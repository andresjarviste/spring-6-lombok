package guru.springframework.restmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import guru.springframework.restmvc.model.Beer;
import guru.springframework.restmvc.services.BeerService;
import guru.springframework.restmvc.services.BeerServiceImpl;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.verify;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import java.util.HashMap;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@WebMvcTest(BeerController.class)
@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Beer Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerMap)))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
            
        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertEquals(beer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(beerMap.get("beerName"), beerArgumentCaptor.getValue().getBeerName());
    }


    @Test
    void testDeleteBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        
        ArgumentCaptor<UUID> argument = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteBeerById(argument.capture());

        assertEquals(beer.getId(), argument.getValue());
    }

    @Test
    void testUpdateBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beer)))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
    }

    @Test
    void testCreateBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        beer.setVersion(null);
        beer.setId(null);
        
        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beer)))
            .andExpect(status().isCreated())
            .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(MockMvcRequestBuilders
            .get(BeerController.BEER_PATH_ID, testBeer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
            .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(MockMvcRequestBuilders
            .get(BeerController.BEER_PATH)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void testGetBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
        
}
