package com.beers.integration;

import com.beers.repository.configuration.BeerRepository;
import com.beers.repository.model.Beer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryIntegrationTest {

    @Autowired
    private BeerRepository beerRepository;

    @Test
    public void beerCreation_returningId(){
        //GIVEN
        Beer beer = buildBeer();

        //WHEN
        Beer beerCreated = beerRepository.save(beer);

        //THEN
        Assertions.assertNotNull(beerCreated.getId());
    }

    @Test
    public void getCatalogueBeerId_returningBeerById() {

        //GIVEN
        Beer beer = buildBeer();
        Beer beerCreated = beerRepository.save(beer);

        //WHEN
        Optional<Beer> beerRetrieved = beerRepository.findById(beerCreated.getId());

        //THEN
        Assertions.assertEquals(beerCreated.getId(), beerRetrieved.get().getId());
        Assertions.assertEquals(beer.getManufacturer(), beerRetrieved.get().getManufacturer());
        Assertions.assertEquals(beer.getName(), beerRetrieved.get().getName());
        Assertions.assertEquals(beer.getGraduation(), beerRetrieved.get().getGraduation());

    }

    @Test
    public void getCatalogueBeerId_returningListofBeers() {

        //GIVEN
        Beer beer = buildBeer();
        Beer beerCreated = beerRepository.save(beer);

        //WHEN
        List<Beer> listBeers = beerRepository.findAll();

        //THEN
        Assertions.assertEquals(1, listBeers.size());
        Assertions.assertEquals(beerCreated.getId(), listBeers.get(0).getId());
        Assertions.assertEquals(beer.getManufacturer(), listBeers.get(0).getManufacturer());
        Assertions.assertEquals(beer.getName(), listBeers.get(0).getName());
        Assertions.assertEquals(beer.getGraduation(), listBeers.get(0).getGraduation());

    }

    @Test
    public void updatedDataBeer_returningNewBeer() {
        //GIVEN
        Beer beer = buildBeer();

        Beer beerCreated = beerRepository.save(beer);
        beerCreated.setGraduation(48);
        beerCreated.setManufacturer("new manufacturer");
        beerCreated.setName("new name");

        //WHEN
        Beer beerUpdated = beerRepository.save(beerCreated);

        Assertions.assertEquals(beerUpdated.getId(), beerCreated.getId());
        Assertions.assertEquals("new manufacturer", beerUpdated.getManufacturer());
        Assertions.assertEquals("new name", beerUpdated.getName());
        Assertions.assertEquals(48, beerUpdated.getGraduation());
    }

    @Test
    public void deleteBeerId() {
        //GIVEN
        Beer beer = buildBeer();
        Beer beerCreated = beerRepository.save(beer);

        //WHEN
        beerRepository.deleteById(beerCreated.getId());

        //THEN
        Optional<Beer> beerDeleted = beerRepository.findById(beerCreated.getId());
        Assertions.assertFalse(beerDeleted.isPresent());
    }

    private Beer buildBeer() {
        return Beer.builder()
                .graduation(98)
                .manufacturer("beer manufacturer")
                .name("beer name")
                .build();
    }

}
