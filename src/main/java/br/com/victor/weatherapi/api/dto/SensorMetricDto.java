package br.com.victor.weatherapi.api.dto;

import br.com.victor.weatherapi.api.enums.MetricType;

import java.util.HashMap;

public class SensorMetricDto {
    private String sensorId;
    private HashMap<MetricType, Double> metrics;

    public SensorMetricDto() {
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