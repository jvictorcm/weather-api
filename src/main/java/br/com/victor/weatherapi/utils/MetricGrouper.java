package br.com.victor.weatherapi.utils;

import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.model.SensorMetric;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MetricGrouper {

    public static Map<String, List<SensorMetric>> groupBySensorId(List<SensorMetric> sensorMetrics) {
        return sensorMetrics.stream()
                .collect(Collectors.groupingBy(SensorMetric::getSensorId));
    }

    public Map<String, List<Double>> extractMetricValues(List<SensorMetric> sensorMetrics, MetricType metricType) {
        return sensorMetrics.stream()
                .collect(Collectors.groupingBy(
                        SensorMetric::getSensorId,
                        Collectors.mapping(metric -> getMetricValue(metric, metricType),
                                Collectors.toList())
                ));
    }

    private Double getMetricValue(SensorMetric sensorMetric, MetricType metricType) {
        return switch (metricType) {
            case TEMPERATURE -> sensorMetric.getTemperature();
            case HUMIDITY-> sensorMetric.getHumidity();
            case WINDSPEED -> sensorMetric.getWindSpeed();
            default -> null;
        };
    }
}