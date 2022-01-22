package com.bskyb.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeersController {

    @GetMapping("/getCatalogueBeerId")
    public String getCatalogueBeerId() {
        return "Hello caalogue product id";
    }
}
