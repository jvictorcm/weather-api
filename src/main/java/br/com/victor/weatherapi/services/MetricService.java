package br.com.victor.weatherapi.services;

import br.com.victor.weatherapi.api.dto.MetricsDto;
import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.mappers.MetricMapper;
import br.com.victor.weatherapi.model.Metric;
import br.com.victor.weatherapi.repositories.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MetricService {
    @Autowired
    MetricRepository metricRepository;


    public MetricsDto addMetric(MetricsDto metric) {
        Metric newMetric = MetricMapper.toEntity(metric);
        Metric savedMetric = metricRepository.save(newMetric);
        return MetricMapper.toDto(savedMetric);
    }

    public MetricsStatisticsDto getMetrics(List<String> sensorIds, List<String> metrics, String statistic, LocalDateTime startDate, LocalDateTime endDate) {
        List<Metric> metricsList = metricRepository.findBySensorIdInAndTimestampBetween(sensorIds, startDate, endDate);
        Map<String, List<Metric>> metricsGroupedBySensorId = metricsList.stream()
                .collect(Collectors.groupingBy(Metric::getSensorId));
        MetricsStatisticsDto response = new MetricsStatisticsDto();
        ArrayList<MetricsDto> statisticList = new ArrayList<>();
        for (Map.Entry<String, List<Metric>> entry : metricsGroupedBySensorId.entrySet()) {
            Map<String, Double> sensorMetrics = new HashMap<>();
            for (String metric : metrics) {
                double metricValue = 0;
                switch (statistic) {
                    case "average":
                        metricValue = getAverageStatistic(entry, metric);
                        break;
                    case "max":
                        metricValue = getMaxStatistic(entry, metric);
                        break;
                    case "min":
                        metricValue = getMinStatistic(entry, metric);
                        break;
                }
                sensorMetrics.put(metric, metricValue);
            }
            statisticList.add(compileStaticsIntoDto(sensorMetrics, entry.getKey()));
        }
        response.setMetricsList(metricsList.stream().map(MetricMapper::toDto).collect(Collectors.toList()));
        response.setStatistics(statisticList);
        return response;
    }

    private MetricsDto compileStaticsIntoDto(Map<String, Double> sensorMetrics, String key) {
        MetricsDto resultMetric = new MetricsDto();
        resultMetric.setSensorId(key);
        resultMetric.getMetrics().putAll(sensorMetrics);
        return resultMetric;
    }

    private static double getMinStatistic(Map.Entry<String, List<Metric>> entry, String metric) {
        double metricValue;
        metricValue = entry.getValue().stream()
                .mapToDouble(x -> {
                    try {
                        Field field = x.getClass().getDeclaredField(metric);
                        field.setAccessible(true);
                        return (double) field.get(x);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .min().orElse(0);
        return metricValue;
    }

    private static double getMaxStatistic(Map.Entry<String, List<Metric>> entry, String metric) {
        double metricValue;
        metricValue = entry.getValue().stream()
                .mapToDouble(x -> {
                    try {
                        Field field = x.getClass().getDeclaredField(metric);
                        field.setAccessible(true);
                        return (double) field.get(x);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .max().orElse(0);
        return metricValue;
    }

    private static double getAverageStatistic(Map.Entry<String, List<Metric>> entry, String metric) {
        double metricValue;
        metricValue = entry.getValue().stream()
                .mapToDouble(x -> {
                    try {
                        Field field = x.getClass().getDeclaredField(metric);
                        field.setAccessible(true);
                        return (double) field.get(x);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .average().orElse(0);
        return metricValue;
    }

}
