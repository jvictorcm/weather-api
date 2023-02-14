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

    Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    MetricService metricService;

    @Override
    public ResponseEntity<MetricsDto> addMetric(MetricsDto metric) {
        LOGGER.info("Received request to create metric for sensor id: {}", metric.getSensorId());
        MetricsDto result = metricService.addMetric(metric);
        LOGGER.info("Successfully created metric for sensor id: {}", result.getSensorId());
        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MetricsStatisticsDto> getMetrics(List<String> sensorIds, List<String> metrics, String statistic, LocalDateTime startDate, LocalDateTime endDate) {
        LOGGER.info("Received GET /metrics request with parameters: sensorIds={}, metrics={}, statistic={}, startDate={}, endDate={}",
                sensorIds, metrics, statistic, startDate, endDate);
        long startTime = System.currentTimeMillis();
        MetricsStatisticsDto response = metricService.getMetrics(sensorIds, metrics, statistic, startDate, endDate);
        long responseTime = System.currentTimeMillis() - startTime;
        LOGGER.info("GET /metrics request completed in {}ms with status code {}", responseTime, HttpStatus.OK.value());
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
