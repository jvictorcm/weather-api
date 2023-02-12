package br.com.victor.weatherapi.api.impl;

import br.com.victor.weatherapi.api.Controller;
import br.com.victor.weatherapi.api.dto.MetricsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController implements Controller {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Override
    public ResponseEntity<MetricsDto> addMetric(MetricsDto metric) {
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
