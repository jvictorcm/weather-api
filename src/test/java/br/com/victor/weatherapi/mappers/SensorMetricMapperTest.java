package br.com.victor.weatherapi.mappers;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.model.SensorMetric;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorMetricMapperTest {

    @Test
    public void testToDto_whenMetricHasValidData_shouldReturnMetricsDto() {
        SensorMetric sensorMetric = new SensorMetric();
        sensorMetric.setSensorId("1");
        sensorMetric.setTemperature(10.5);
        sensorMetric.setHumidity(10.0);
        SensorMetricDto sensorMetricDto = MetricMapper.toDto(sensorMetric);

        assertAll("Sensor Metric Mapper Test",
                () -> assertEquals("1", sensorMetricDto.getSensorId(),
                        "Should have mapped the id 1 to the sensorId"),

                () -> assertEquals(10.5, sensorMetricDto.getMetrics().get(MetricType.TEMPERATURE), 0.0,
                        "Temperature value should be 10.5"),

                () -> assertEquals(10.0, sensorMetricDto.getMetrics().get(MetricType.HUMIDITY), 0.0,
                        "Humidity value should be 10"),

                () -> assertNull(sensorMetricDto.getMetrics().get(MetricType.WINDSPEED),
                        "WindSpeed value should be null")
        );
    }

    @Test
    public void testToEntity() {
        SensorMetricDto dto = new SensorMetricDto();
        dto.setSensorId("123");
        dto.getMetrics().put(MetricType.TEMPERATURE, 72.0);
        dto.getMetrics().put(MetricType.HUMIDITY, 0.65);
        dto.getMetrics().put(MetricType.WINDSPEED, 10.0);
        SensorMetric sensorMetric = MetricMapper.toEntity(dto);
        assertAll("Sensor Metric Mapper Test",
                () -> assertEquals("123", sensorMetric.getSensorId(),
                        "Should have mapped the id 123 to the sensorId"),

                () -> assertEquals(72.0, sensorMetric.getTemperature(), 0.0,
                        "Temperature value should be 72.0"),

                () -> assertEquals(0.65, sensorMetric.getHumidity(), 0.0,
                        "Humidity value should be 0.65"),

                () -> assertEquals(10.0, sensorMetric.getWindSpeed(), 0.0,
                        "WindSpeed value should be 10.0")
        );
    }

    @Test
    public void testToEntityWithMissingMetrics() {
        SensorMetricDto dto = new SensorMetricDto();
        dto.setSensorId("123");
        dto.getMetrics().put(MetricType.TEMPERATURE, 72.0);
        dto.getMetrics().put(MetricType.HUMIDITY, 0.65);
        SensorMetric sensorMetric = MetricMapper.toEntity(dto);

        assertAll("Sensor Metric Mapper Test",
                () -> assertEquals("123", sensorMetric.getSensorId(),
                        "Should have mapped the id 123 to the sensorId"),

                () -> assertEquals(72.0, sensorMetric.getTemperature(), 0.0,
                        "Temperature value should be 72.0"),

                () -> assertEquals(0.65, sensorMetric.getHumidity(), 0.0,
                        "Humidity value should be 0.65"),

                () -> assertNull(sensorMetric.getWindSpeed(),
                        "WindSpeed value should be null")
        );
    }
}