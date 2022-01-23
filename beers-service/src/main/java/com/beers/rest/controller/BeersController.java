package com.beers.rest.controller;

import com.beers.repository.model.Beer;
import com.beers.repository.configuration.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BeersController {

    @Autowired
    private BeerRepository repository;

    @GetMapping(value="/getCatalogueBeerId", produces = MediaType.APPLICATION_JSON_VALUE, params = {"beerId"})
    public ResponseEntity<?> getCatalogueBeerId(@RequestParam(required = false) String beerId) {

        Optional<Beer> beer = repository.findById(beerId);

        if (beer.isPresent()) {
            return new ResponseEntity<>(beer, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value="/getAllBeers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBeers() {

        List<Beer> beers = repository.findAll();

        if (beers.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(beers, HttpStatus.OK);
        }
    }

    @ResponseBody
    @PostMapping(value = "/addingBeer")
    public ResponseEntity<?> addingBeer(@RequestBody Beer beer) {

        Beer savedBeer = repository.save(beer);

        if (savedBeer != null) {
            return new ResponseEntity<>(beer.getId(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value="/deleteCatalogueBeerId", params = {"beerId"})
    public ResponseEntity<?> deleteBeer(@RequestParam String beerId) {

        if(repository.existsById(beerId)) {
            repository.deleteById(beerId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

    }

    @PutMapping(value="/updateBeer", params={"beerId"})
    public ResponseEntity<?> updateBeer(@RequestParam String beerId, @RequestBody Beer beer) {

        Optional<Beer> beerUpdate = repository.findById(beerId);
        if(beerUpdate.isPresent()) {
            beerUpdate.get().setManufacturer(beer.getManufacturer());
            beerUpdate.get().setName(beer.getName());
            beerUpdate.get().setGraduation(beer.getGraduation());
            Beer savedBeer = repository.save(beerUpdate.get());

            return new ResponseEntity<String>(savedBeer.getId(), HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

    }



}
