package br.com.victor.weatherapi.api.dto;

import br.com.victor.weatherapi.api.enums.MetricType;

import java.util.HashMap;

public class WeatherMetricDto {
    private String sensorId;
    private HashMap<MetricType, Double> metrics;

    public WeatherMetricDto(String sensorId, HashMap<MetricType, Double> metrics) {
        this.sensorId = sensorId;
        this.metrics = metrics;
    }

    public WeatherMetricDto() {
        this.metrics = new HashMap<>();
    }

    public String getSensorId() {
        return sensorId;
    }

    public HashMap<MetricType, Double> getMetrics() {
        return metrics;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}