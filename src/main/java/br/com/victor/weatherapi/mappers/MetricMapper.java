package br.com.victor.weatherapi.mappers;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.model.SensorMetric;
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

    public static SensorMetric toEntity(SensorMetricDto sensorMetricDto) {
        SensorMetric sensorMetric = new SensorMetric();
        sensorMetric.setSensorId(sensorMetricDto.getSensorId());
        sensorMetric.setTemperature(sensorMetricDto.getMetrics().get(MetricType.TEMPERATURE));
        sensorMetric.setHumidity(sensorMetricDto.getMetrics().get(MetricType.HUMIDITY));
        sensorMetric.setWindSpeed(sensorMetricDto.getMetrics().get(MetricType.WINDSPEED));
        return sensorMetric;
    }

    public static SensorMetricDto toDto(SensorMetric sensorMetric) {
        LOGGER.debug("Converting MetricEntity to DTO: {}", sensorMetric);
        long startTime = System.currentTimeMillis();
        SensorMetricDto sensorMetricDto = new SensorMetricDto();
        sensorMetricDto.setSensorId(sensorMetric.getSensorId());
        ArrayList<Field> fieldList = new ArrayList<>(Arrays.stream(sensorMetric.getClass().getDeclaredFields()).collect(Collectors.toList()));
        fieldList.removeIf(x -> ignoreList.contains(x.getName()));
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(sensorMetric);
                if (fieldValue != null)
                    sensorMetricDto.getMetrics().put(MetricType.fromValue(field.getName()), (double) fieldValue);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException("Could not access field: " + field.getName(), ex);
            }
        }
        long responseTime = System.currentTimeMillis() - startTime;
        LOGGER.debug("Finished conversion of MetricEntity to DTO: {}", responseTime);
        return sensorMetricDto;
    }
}