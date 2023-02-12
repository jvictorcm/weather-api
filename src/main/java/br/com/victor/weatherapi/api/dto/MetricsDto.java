package br.com.victor.weatherapi.api.dto;

import java.util.HashMap;

public class MetricsDto {
    private String sensorId;
    private HashMap<String, Double> metrics;

    public MetricsDto(String sensorId, HashMap<String, Double> metrics) {
        this.sensorId = sensorId;
        this.metrics = metrics;
    }

    public MetricsDto() {
        this.metrics = new HashMap<>();
    }

    public String getSensorId() {
        return sensorId;
    }

    public HashMap<String, Double> getMetrics() {
        return metrics;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}