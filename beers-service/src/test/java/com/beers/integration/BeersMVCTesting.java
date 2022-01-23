package com.beers.integration;

import com.beers.repository.configuration.BeerRepository;
import com.beers.repository.model.Beer;
import com.beers.rest.controller.BeersController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeersController.class)
public class BeersMVCTesting {

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

    @Test
    public void addingBeer_returning_ok() throws Exception {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.save(ArgumentMatchers.any(Beer.class))).thenReturn(beer);

        //THEN
        mockMvc.perform(post("/addingBeer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beer)))
                .andExpect(status().isOk())
                .andExpect(content().string(beer.getId()))
                .andReturn();

    }

    @Test
    public void addingBeer_returning_not_found() throws Exception {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.save(ArgumentMatchers.any(Beer.class))).thenReturn(null);

        //THEN
        mockMvc.perform(post("/addingBeer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beer)))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void updateBeer_returning_ok() throws Exception {

        //GIVEN
        Beer newBeer = Beer.builder()
                .manufacturer("new manufacturer")
                .graduation(57)
                .id("1")
                .name("new name").build();


        when(repository.save(ArgumentMatchers.any(Beer.class))).thenReturn(newBeer);
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(newBeer));


        //THEN
        mockMvc.perform(put("/updateBeer")
                .param("beerId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newBeer)))
                .andExpect(status().isOk())
                .andExpect(content().string(newBeer.getId()))
                .andReturn();

    }

    @Test
    public void updateBeer_returning_bad_request() throws Exception {

        //GIVEN
        Beer newBeer = Beer.builder()
                .manufacturer("new manufacturer")
                .graduation(57)
                .id("1")
                .name("new name").build();


        when(repository.save(ArgumentMatchers.any(Beer.class))).thenReturn(newBeer);
        when(repository.findById(anyString())).thenReturn(Optional.empty());


        //THEN
        mockMvc.perform(put("/updateBeer")
                .param("beerId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newBeer)))
                .andExpect(status().isBadRequest())
                .andReturn();

    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Beer buildBeer() {
        return Beer.builder()
                .graduation(98)
                .manufacturer("beer manufacturer")
                .name("beer name")
                .build();
    }
}
