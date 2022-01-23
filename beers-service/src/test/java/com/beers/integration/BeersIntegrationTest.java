package com.beers.integration;

import com.beers.BeersApplication;
import com.beers.repository.configuration.BeerRepository;
import com.beers.repository.model.Beer;
import com.beers.rest.controller.BeersController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={BeersApplication.class})
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
        Assertions.assertEquals(beer.getId(), (String)rest.getBody());
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
        Optional<Beer> beerRetrieved = (Optional<Beer>)rest.getBody();

        Assertions.assertEquals(beerRetrieved.get().getId(), beer.getId());
        Assertions.assertEquals(beerRetrieved.get().getName(), beer.getName());
        Assertions.assertEquals(beerRetrieved.get().getManufacturer(), beer.getManufacturer());
        Assertions.assertEquals(beerRetrieved.get().getGraduation(), beer.getGraduation());
    }


    private Beer buildBeer() {
        return Beer.builder()
                .graduation(98)
                .manufacturer("beer manufacturer")
                .name("beer name")
                .build();
    }
}
