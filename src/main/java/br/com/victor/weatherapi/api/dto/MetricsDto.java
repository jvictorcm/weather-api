package br.com.victor.weatherapi.api.dto;

import java.util.Map;

public class MetricsDto {
    private String sensorId;
    private Map<String, Double> metrics;

    public MetricsDto(String sensorId, Map<String, Double> metrics) {
        this.sensorId = sensorId;
        this.metrics = metrics;
    }

    public String getSensorId() {
        return sensorId;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }
}