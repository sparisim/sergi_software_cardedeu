package com.beers.integration;

import com.beers.BeersApplication;
import com.beers.repository.model.Beer;
import com.beers.rest.controller.BeersController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={BeersApplication.class})
public class BeersIntegrationTest {

    @Autowired
    private BeersController beersController;

    @Test
    public void addingBeerId_returning_OK() {
        //GIVEN
        Beer beer = buildBeer();

        //WHEN
        ResponseEntity<?> rest = beersController.addingBeer(beer);


        //THEN
        Assertions.assertEquals(HttpStatus.OK, rest.getStatusCode());
        Assertions.assertNotNull((String)rest.getBody());
    }


    private Beer buildBeer() {
        return Beer.builder()
                .graduation(98)
                .manufacturer("beer manufacturer")
                .name("beer name")
                .build();
    }
}
