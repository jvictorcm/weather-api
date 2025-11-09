package br.com.victor.weatherapi.services;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.enums.StatisticType;
import br.com.victor.weatherapi.mappers.MetricMapper;
import br.com.victor.weatherapi.model.SensorMetric;
import br.com.victor.weatherapi.repositories.MetricRepository;
import br.com.victor.weatherapi.utils.MetricGrouper;
import br.com.victor.weatherapi.utils.StatisticsCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SensorMetricService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorMetricService.class);

    private final MetricRepository metricRepository;
    private StatisticsCalculator statisticsCalculator;
    private MetricGrouper metricGrouper;

    public SensorMetricService(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
        this.statisticsCalculator = new StatisticsCalculator();
        this.metricGrouper = new MetricGrouper();
    }

    public SensorMetricDto addMetric(SensorMetricDto metric) {
        LOGGER.info("Adding metric for sensor: {}", metric.getSensorId());
        SensorMetric newSensorMetric = MetricMapper.toEntity(metric);
        SensorMetric savedSensorMetric = metricRepository.save(newSensorMetric);
        return MetricMapper.toDto(savedSensorMetric);
    }

    public MetricsStatisticsDto getMetrics(List<String> sensorIds,
                                           List<MetricType> metrics,
                                           StatisticType statistic,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate) {
        LOGGER.debug("Fetching metrics with parameters: sensors={}, metrics={}, statistic={}",
                sensorIds, metrics, statistic);

        List<SensorMetric> metricsList = metricRepository.findByParameters(sensorIds, startDate, endDate);

        if (metricsList.isEmpty()) {
            LOGGER.info("No metrics found for the given criteria");
            return createEmptyResponse();
        }

        MetricsStatisticsDto response = new MetricsStatisticsDto();
        response.setMetricsList(metricsList.stream().map(MetricMapper::toDto).collect(Collectors.toList()));
        response.setStatistics(calculateStatistics(metricsList, metrics, statistic));

        return response;
    }

    private List<SensorMetricDto> calculateStatistics(List<SensorMetric> sensorMetrics,
                                                      List<MetricType> metricTypes,
                                                      StatisticType statistic) {

        Map<String, List<SensorMetric>> groupedMetrics = metricGrouper.groupBySensorId(sensorMetrics);
        Set<Map.Entry<String, List<SensorMetric>>> entrySet = groupedMetrics.entrySet();
        Stream<Map.Entry<String, List<SensorMetric>>> stream = entrySet.stream();
        Stream<SensorMetricDto> resultStream = stream.map(entry -> {
            String sensorId = entry.getKey();
            List<SensorMetric> sensorMetricsForSensor = entry.getValue();

            System.out.println("Processing sensor: " + sensorId + " with " + sensorMetricsForSensor.size() + " metrics");

            SensorMetricDto result = calculateSensorStatistics(sensorId, sensorMetricsForSensor, metricTypes, statistic);

            System.out.println("Result for sensor " + sensorId + ": " + result);
            return result;
        });
        List<SensorMetricDto> results = resultStream.collect(Collectors.toList());
        return results;

//        return metricGrouper.groupBySensorId(sensorMetrics).entrySet().stream()
//                .map(entry -> calculateSensorStatistics(entry.getKey(), entry.getValue(), metricTypes, statistic))
//                .collect(Collectors.toList());
    }

    private SensorMetricDto calculateSensorStatistics(String sensorId,
                                                      List<SensorMetric> sensorSensorMetrics,
                                                      List<MetricType> metricTypes,
                                                      StatisticType statistic) {
        Map<MetricType, Double> statistics = new HashMap<>();

        for (MetricType localMetricType : metricTypes) {
            List<Double> values = sensorSensorMetrics.stream()
                    .map(metric -> statisticsCalculator.getMetricValue(metric, localMetricType))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!values.isEmpty()) {
                double calculatedValue = statisticsCalculator.calculateStatistic(values, statistic);
                statistics.put(localMetricType, calculatedValue);
            }
        }

        return compileStatisticsIntoDto(statistics, sensorId);
    }

    private SensorMetricDto compileStatisticsIntoDto(Map<MetricType, Double> statistics, String sensorId) {
        SensorMetricDto result = new SensorMetricDto();
        result.setSensorId(sensorId);
        result.getMetrics().putAll(statistics);
        return result;
    }

    private MetricsStatisticsDto createEmptyResponse() {
        MetricsStatisticsDto response = new MetricsStatisticsDto();
        response.setMetricsList(Collections.emptyList());
        response.setStatistics(Collections.emptyList());
        return response;
    }
}