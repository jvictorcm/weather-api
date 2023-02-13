package br.com.victor.weatherapi.mappers;

import br.com.victor.weatherapi.api.dto.MetricsDto;
import br.com.victor.weatherapi.model.Metric;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetricMapper {

    static List<String> ignoreList = Arrays.asList("id", "createdAt", "sensorId");

    public static Metric toEntity(MetricsDto metricsDto) {
        Metric metric = new Metric();
        metric.setSensorId(metricsDto.getSensorId());
        metric.setTemperature(metricsDto.getMetrics().get("temperature"));
        metric.setHumidity(metricsDto.getMetrics().get("humidity"));
        metric.setWindSpeed(metricsDto.getMetrics().get("windSpeed"));
        return metric;
    }

    public static MetricsDto toDto(Metric metric) {
        MetricsDto metricsDto = new MetricsDto();
        metricsDto.setSensorId(metric.getSensorId());
        ArrayList<Field> fieldList = new ArrayList<>(Arrays.stream(metric.getClass().getDeclaredFields()).collect(Collectors.toList()));
        fieldList.removeIf(x -> ignoreList.contains(x.getName()));
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(metric);
                if (fieldValue != null)
                    metricsDto.getMetrics().put(field.getName(), (double) fieldValue);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Could not access field: " + field.getName(), ex);
            }
        }
        return metricsDto;
    }
}