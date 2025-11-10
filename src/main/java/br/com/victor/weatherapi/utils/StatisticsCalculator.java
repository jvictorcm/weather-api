package br.com.victor.weatherapi.utils;

import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.enums.StatisticType;
import br.com.victor.weatherapi.model.SensorMetric;

import java.util.List;

public class StatisticsCalculator {

    public double calculateStatistic(List<Double> values, StatisticType statistic) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }

        return switch (statistic) {
            case AVERAGE -> values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            case MAX -> values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
            case MIN -> values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        };
    }

    public Double getMetricValue(SensorMetric sensorMetric, MetricType metricType) {
        return switch (metricType) {
            case TEMPERATURE -> sensorMetric.getTemperature();
            case HUMIDITY -> sensorMetric.getHumidity();
            case WINDSPEED -> sensorMetric.getWindSpeed();
            default -> throw new IllegalArgumentException("Unknown metric: " + metricType);
        };
    }
}