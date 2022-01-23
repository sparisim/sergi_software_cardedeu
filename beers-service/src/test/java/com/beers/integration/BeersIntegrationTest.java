package com.beers.integration;

import com.beers.BeersApplication;
import com.beers.repository.configuration.BeerRepository;
import com.beers.repository.model.Beer;
import com.beers.rest.controller.BeersController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BeersApplication.class})
public class BeersIntegrationTest {

    @Autowired
    private BeersController beersController;

    @MockBean
    private BeerRepository repository;

    @Test
    public void addingBeerId_returning_OK() {
        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.save(any(Beer.class))).thenReturn(beer);

        //WHEN
        ResponseEntity<?> rest = beersController.addingBeer(beer);


        //THEN
        Assertions.assertEquals(HttpStatus.OK, rest.getStatusCode());
        Assertions.assertEquals(beer.getId(), (String) rest.getBody());
    }

    @Test
    public void addingBeerId_returning_NOT_FOUND() {
        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        //WHEN
        ResponseEntity<?> rest = beersController.addingBeer(beer);


        //THEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rest.getStatusCode());
    }

    @Test
    public void getCatalogueProductId_returning_ok() {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");
        when(repository.findById(anyString())).thenReturn(Optional.of(beer));

        //WHEN
        ResponseEntity<?> rest = beersController.getCatalogueBeerId(beer.getId());

        //THEN
        Optional<Beer> beerRetrieved = (Optional<Beer>) rest.getBody();

        Assertions.assertEquals(beerRetrieved.get().getId(), beer.getId());
        Assertions.assertEquals(beerRetrieved.get().getName(), beer.getName());
        Assertions.assertEquals(beerRetrieved.get().getManufacturer(), beer.getManufacturer());
        Assertions.assertEquals(beerRetrieved.get().getGraduation(), beer.getGraduation());
    }

    @Test
    public void getCatalogueProductId_returning_not_found() {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        //WHEN
        ResponseEntity<?> rest = beersController.getCatalogueBeerId(beer.getId());

        //THEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rest.getStatusCode());
    }

    @Test
    public void getAllBeers_returning_OK() throws Exception {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        List<Beer> listBeers = Arrays.asList(beer);

        when(repository.findAll()).thenReturn(listBeers);

        //WHEN
        ResponseEntity<?> rest = beersController.getAllBeers();

        //THEN
        List<Beer> beers = (List<Beer>) rest.getBody();
        Assertions.assertEquals(HttpStatus.OK, rest.getStatusCode());
        Assertions.assertEquals(1, beers.size());
        Assertions.assertEquals(beers.get(0).getId(), beer.getId());
        Assertions.assertEquals(beers.get(0).getName(), beer.getName());
        Assertions.assertEquals(beers.get(0).getManufacturer(), beer.getManufacturer());
        Assertions.assertEquals(beers.get(0).getGraduation(), beer.getGraduation());

    }

    @Test
    public void getAllBeers_returning_not_found() throws Exception {

        //GIVEN

        when(repository.findAll()).thenReturn(new ArrayList<>());

        //WHEN
        ResponseEntity<?> rest = beersController.getAllBeers();

        //THEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rest.getStatusCode());
    }


    @Test
    public void deleteBeers_returning_OK() throws Exception {

        //GIVEN
        when(repository.existsById(anyString())).thenReturn(true);
        doNothing().when(repository).deleteById(anyString());

        //WHEN
        ResponseEntity<?> rest = beersController.deleteBeer("1");


        //THEN
        Assertions.assertEquals(HttpStatus.OK, rest.getStatusCode());

    }

    @Test
    public void deleteBeers_returning_NOT_FOUND() throws Exception {

        when(repository.existsById(anyString())).thenReturn(false);
        doNothing().when(repository).deleteById(anyString());

        //WHEN
        ResponseEntity<?> rest = beersController.deleteBeer("1");


        //THEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rest.getStatusCode());
    }

    @Test
    public void addingBeer_returning_ok() throws Exception {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.save(ArgumentMatchers.any(Beer.class))).thenReturn(beer);

        //WHEN
        ResponseEntity<?> rest = beersController.addingBeer(beer);

        //THEN
        Assertions.assertEquals(HttpStatus.OK, rest.getStatusCode());
        Assertions.assertEquals("1", (String) rest.getBody());

    }

    @Test
    public void addingBeer_returning_not_found() throws Exception {

        //GIVEN
        Beer beer = buildBeer();
        beer.setId("1");

        when(repository.save(ArgumentMatchers.any(Beer.class))).thenReturn(null);

        //WHEN
        ResponseEntity<?> rest = beersController.addingBeer(beer);

        //THEN
        Assertions.assertEquals(HttpStatus.NOT_FOUND, rest.getStatusCode());
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

        //WHEN
        ResponseEntity<?> rest = beersController.updateBeer("1", newBeer);

        //THEN
        Assertions.assertEquals(HttpStatus.OK, rest.getStatusCode());
        Assertions.assertEquals("1", (String) rest.getBody());

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

        //WHEN
        ResponseEntity<?> rest = beersController.updateBeer("1", newBeer);

        //THEN
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, rest.getStatusCode());

    }


    private Beer buildBeer() {
        return Beer.builder()
                .graduation(98)
                .manufacturer("beer manufacturer")
                .name("beer name")
                .build();
    }
}
