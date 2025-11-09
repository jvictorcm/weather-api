package br.com.victor.weatherapi.mappers;

import br.com.victor.weatherapi.api.dto.WeatherMetricDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.model.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetricMapper {

    static Logger LOGGER = LoggerFactory.getLogger(MetricMapper.class);
    static List<String> ignoreList = Arrays.asList("id", "createdAt", "sensorId");

    public static Metric toEntity(WeatherMetricDto weatherMetricDto) {
        Metric metric = new Metric();
        metric.setSensorId(weatherMetricDto.getSensorId());
        metric.setTemperature(weatherMetricDto.getMetrics().get(MetricType.TEMPERATURE));
        metric.setHumidity(weatherMetricDto.getMetrics().get(MetricType.HUMIDITY));
        metric.setWindSpeed(weatherMetricDto.getMetrics().get(MetricType.WINDSPEED));
        return metric;
    }

    public static WeatherMetricDto toDto(Metric metric) {
        LOGGER.debug("Converting MetricEntity to DTO: {}", metric);
        long startTime = System.currentTimeMillis();
        WeatherMetricDto weatherMetricDto = new WeatherMetricDto();
        weatherMetricDto.setSensorId(metric.getSensorId());
        ArrayList<Field> fieldList = new ArrayList<>(Arrays.stream(metric.getClass().getDeclaredFields()).collect(Collectors.toList()));
        fieldList.removeIf(x -> ignoreList.contains(x.getName()));
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(metric);
                if (fieldValue != null)
                    weatherMetricDto.getMetrics().put(MetricType.valueOf(field.getName()), (double) fieldValue);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Could not access field: " + field.getName(), ex);
            }
        }
        long responseTime = System.currentTimeMillis() - startTime;
        LOGGER.debug("Finished conversion of MetricEntity to DTO: {}", responseTime);
        return weatherMetricDto;
    }
}