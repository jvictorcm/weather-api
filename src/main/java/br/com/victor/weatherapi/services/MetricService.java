package br.com.victor.weatherapi.services;

import br.com.victor.weatherapi.api.dto.WeatherMetricDto;
import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.enums.StatisticType;
import br.com.victor.weatherapi.mappers.MetricMapper;
import br.com.victor.weatherapi.model.Metric;
import br.com.victor.weatherapi.repositories.MetricRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricService {

    Logger LOGGER = LoggerFactory.getLogger(MetricService.class);

    @Autowired
    MetricRepository metricRepository;


    public WeatherMetricDto addMetric(WeatherMetricDto metric) {
        Metric newMetric = MetricMapper.toEntity(metric);
        Metric savedMetric = metricRepository.save(newMetric);
        return MetricMapper.toDto(savedMetric);
    }

    public MetricsStatisticsDto getMetrics(List<String> sensorIds, List<MetricType> metrics, StatisticType statistic, LocalDateTime startDate, LocalDateTime endDate) {
        List<Metric> metricsList = metricRepository.findByParameters(sensorIds, startDate, endDate);
        Map<String, List<Metric>> metricsGroupedBySensorId = metricsList.stream()
                .collect(Collectors.groupingBy(Metric::getSensorId));
        MetricsStatisticsDto response = new MetricsStatisticsDto();
        ArrayList<WeatherMetricDto> statisticList = new ArrayList<>();
        for (Map.Entry<String, List<Metric>> entry : metricsGroupedBySensorId.entrySet()) {
            Map<MetricType, Double> sensorMetrics = new HashMap<>();
            for (MetricType metric : metrics) {
                double metricValue = 0;
                switch (statistic) {
                    case AVERAGE:
                        metricValue = getAverageStatistic(entry, metric);
                        break;
                    case MAX:
                        metricValue = getMaxStatistic(entry, metric);
                        break;
                    case MIN:
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

    private WeatherMetricDto compileStaticsIntoDto(Map<MetricType, Double> sensorMetrics, String key) {
        WeatherMetricDto resultMetric = new WeatherMetricDto();
        resultMetric.setSensorId(key);
        resultMetric.getMetrics().putAll(sensorMetrics);
        return resultMetric;
    }

    private static double getMinStatistic(Map.Entry<String, List<Metric>> entry, MetricType metric) {
        double metricValue;
        metricValue = entry.getValue().stream()
                .mapToDouble(x -> {
                    try {
                        Field field = x.getClass().getDeclaredField(metric.getValue());
                        field.setAccessible(true);
                        return (double) field.get(x);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .min().orElse(0);
        return metricValue;
    }

    private static double getMaxStatistic(Map.Entry<String, List<Metric>> entry, MetricType metric) {
        double metricValue;
        metricValue = entry.getValue().stream()
                .mapToDouble(x -> {
                    try {
                        Field field = x.getClass().getDeclaredField(metric.getValue());
                        field.setAccessible(true);
                        return (double) field.get(x);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .max().orElse(0);
        return metricValue;
    }

    private static double getAverageStatistic(Map.Entry<String, List<Metric>> entry, MetricType metric) {
        double metricValue;
        metricValue = entry.getValue().stream()
                .mapToDouble(x -> {
                    try {
                        Field field = x.getClass().getDeclaredField(metric.getValue());
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
