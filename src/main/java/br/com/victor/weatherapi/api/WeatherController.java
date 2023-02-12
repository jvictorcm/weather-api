package br.com.victor.weatherapi.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weathers")
public class WeatherController {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);


    @PostMapping
    public ResponseEntity<Object> createWeather(@RequestBody Object weatherInput) {
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
