package br.com.victor.weatherapi.api.impl;

import br.com.victor.weatherapi.api.Controller;
import br.com.victor.weatherapi.api.dto.MetricsDto;
import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.services.MetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class WeatherController implements Controller {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    MetricService metricService;

    @Override
    public ResponseEntity<MetricsDto> addMetric(MetricsDto metric) {
        return new ResponseEntity(metricService.addMetric(metric), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MetricsStatisticsDto> getMetrics(List<String> sensorIds, List<String> metrics, String statistic, LocalDateTime startDate, LocalDateTime endDate) {
        return new ResponseEntity(metricService.getMetrics(sensorIds, metrics, statistic, startDate, endDate), HttpStatus.OK);
    }
}
