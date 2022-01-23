package com.beers.integration;

import com.beers.repository.configuration.BeerRepository;
import com.beers.repository.model.Beer;
import com.beers.rest.controller.BeersController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeersController.class)
public class BeeersMVCTesting {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerRepository repository;

    @Test
    public void getCatalogueBeerById_returning_OK() throws Exception{

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.findById(anyString())).thenReturn(Optional.of(beer));

        //THEN
        mockMvc.perform(get("/getCatalogueBeerId?beerId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(beer.getId())))
                .andExpect(jsonPath("$.manufacturer", Matchers.is(beer.getManufacturer())))
                .andExpect(jsonPath("$.name", Matchers.is(beer.getName())))
                .andExpect(jsonPath("$.graduation", Matchers.is(beer.getGraduation())));
    }

    @Test
    public void getCatalogueBeerById_returning_NOT_FOUND() throws Exception{

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        //THEN
        mockMvc.perform(get("/getCatalogueBeerId?beerId=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllBeers_returning_OK() throws Exception{

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        List<Beer> listBeers = Arrays.asList(beer);

        when(repository.findAll()).thenReturn(listBeers);

        //THEN
        mockMvc.perform(get("/getAllBeers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(beer.getId())))
                .andExpect(jsonPath("$[0].manufacturer", Matchers.is(beer.getManufacturer())))
                .andExpect(jsonPath("$[0].name", Matchers.is(beer.getName())))
                .andExpect(jsonPath("$[0].graduation", Matchers.is(beer.getGraduation())));
    }

    @Test
    public void getAllBeers_returning_NOT_FOUND() throws Exception{

        //GIVEN
        when(repository.findAll()).thenReturn(new ArrayList<Beer>());

        //THEN
        mockMvc.perform(get("/getAllBeers"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBeers_returning_OK() throws Exception {

        //GIVEN
        when(repository.existsById(anyString())).thenReturn(true);
        doNothing().when(repository).deleteById(anyString());


        //THEN
        mockMvc.perform(delete("/deleteCatalogueBeerId?beerId=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBeers_returning_NOT_FOUND() throws Exception {

        //GIVEN
        when(repository.existsById(anyString())).thenReturn(false);

        //THEN
        mockMvc.perform(delete("/deleteCatalogueBeerId?beerId=1"))
                .andExpect(status().isNotFound());
    }


    private Beer buildBeer() {
        return Beer.builder()
                .graduation(98)
                .manufacturer("beer manufacturer")
                .name("beer name")
                .build();
    }
}
