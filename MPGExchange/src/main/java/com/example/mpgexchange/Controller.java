package com.example.mpgexchange.controller;

import com.example.mpgexchange.service.ServiceCache;
import com.example.mpgexchange.util.ModelMPG;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Indicates a REST controller in a Spring Boot application
@RequestMapping("/api/fueleconomy") //URL for API
@CrossOrigin(origins = "http://localhost:3000") //Allow CORS
public class Controller {

    private final ServiceCache serviceCache;

    //Constructor to in inject the ServiceCache dependency
    public Controller(ServiceCache serviceCache) {
        this.serviceCache = serviceCache;
    }

    //endpoint to get fuel economy data for models
    @GetMapping("/toyota")
    public List<ModelMPG> getToyotaData(@RequestParam(required = false) Integer startYear,
                                        @RequestParam(required = false) Integer endYear,
                                        @RequestParam(required = false) String model) {
        if (startYear == null && endYear == null) {
            throw new IllegalArgumentException("Must enter either start or end year.");
        }
        return serviceCache.getFuelEconomyDataForToyota(startYear, endYear, model);
    }
}
