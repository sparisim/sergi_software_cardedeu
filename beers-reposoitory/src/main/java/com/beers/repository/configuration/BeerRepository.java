package com.beers.repository.configuration;

import com.beers.repository.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, String> {

}
