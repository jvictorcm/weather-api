package br.com.victor.weatherapi.api.dto;

import java.util.List;

public class MetricsStatisticsDto {
    private List<SensorMetricDto> metricsList;
    private List<SensorMetricDto> statistics;

    public MetricsStatisticsDto() {
    }

    public List<SensorMetricDto> getMetricsList() {
        return metricsList;
    }

    public void setMetricsList(List<SensorMetricDto> metricsList) {
        this.metricsList = metricsList;
    }

    public List<SensorMetricDto> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<SensorMetricDto> statistics) {
        this.statistics = statistics;
    }
}