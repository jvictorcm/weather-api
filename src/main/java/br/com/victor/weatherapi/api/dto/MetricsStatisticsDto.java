package br.com.victor.weatherapi.api.dto;

import java.util.List;

public class MetricsStatisticsDto {
    private List<MetricsDto> metricsList;
    private List<MetricsDto> statistics;

    public MetricsStatisticsDto() {
    }

    public List<MetricsDto> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<MetricsDto> metricsList) {
        this.metricsList = metricsList;
    }

    public List<MetricsDto> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<MetricsDto> statistics) {
        this.statistics = statistics;
    }
}