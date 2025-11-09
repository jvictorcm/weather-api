package br.com.victor.weatherapi.mappers;

import br.com.victor.weatherapi.api.dto.WeatherMetricDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.model.Metric;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetricMapperTest {

    @Test
    public void testToDto_whenMetricHasValidData_shouldReturnMetricsDto() {
        Metric metric = new Metric();
        metric.setSensorId("1");
        metric.setTemperature(10.5);
        metric.setHumidity(10.0);
        WeatherMetricDto weatherMetricDto = MetricMapper.toDto(metric);
        assertEquals("1", weatherMetricDto.getSensorId());
        assertEquals(10.5, weatherMetricDto.getMetrics().get(MetricType.TEMPERATURE), 0.0);
        assertEquals(10.0, weatherMetricDto.getMetrics().get(MetricType.HUMIDITY), 0.0);
    }

    @Test
    public void testToEntity() {
        WeatherMetricDto dto = new WeatherMetricDto();
        dto.setSensorId("123");
        dto.getMetrics().put(MetricType.TEMPERATURE, 72.0);
        dto.getMetrics().put(MetricType.HUMIDITY, 0.65);
        dto.getMetrics().put(MetricType.WINDSPEED, 10.0);
        Metric metric = MetricMapper.toEntity(dto);
        assertEquals("123", metric.getSensorId());
        assertEquals(72.0, metric.getTemperature(), 0.001);
        assertEquals(0.65, metric.getHumidity(), 0.001);
        assertEquals(10.0, metric.getWindSpeed(), 0.001);
    }

    @Test
    public void testToEntityWithMissingMetrics() {
        WeatherMetricDto dto = new WeatherMetricDto();
        dto.setSensorId("123");
        dto.getMetrics().put(MetricType.TEMPERATURE, 72.0);
        dto.getMetrics().put(MetricType.HUMIDITY, 0.65);
        Metric metric = MetricMapper.toEntity(dto);
        assertEquals("123", metric.getSensorId());
        assertEquals(72.0, metric.getTemperature(), 0.001);
        assertEquals(0.65, metric.getHumidity(), 0.001);
    }
}