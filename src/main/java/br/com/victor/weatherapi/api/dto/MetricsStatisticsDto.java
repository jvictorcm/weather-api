package br.com.victor.weatherapi.api.dto;

import java.util.List;

public class MetricsStatisticsDto {
    private List<WeatherMetricDto> metricsList;
    private List<WeatherMetricDto> statistics;

    public MetricsStatisticsDto() {
    }

    public List<WeatherMetricDto> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<WeatherMetricDto> metricsList) {
        this.metricsList = metricsList;
    }

    public List<WeatherMetricDto> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<WeatherMetricDto> statistics) {
        this.statistics = statistics;
    }
}