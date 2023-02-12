package br.com.victor.weatherapi.mappers;

import br.com.victor.weatherapi.api.dto.MetricsDto;
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
        MetricsDto metricsDto = MetricMapper.toDto(metric);
        assertEquals("1", metricsDto.getSensorId());
        assertEquals(10.5, metricsDto.getMetrics().get("temperature"), 0.0);
        assertEquals(10.0, metricsDto.getMetrics().get("humidity"), 0.0);
    }

    @Test
    public void testToEntity() {
        MetricsDto dto = new MetricsDto();
        dto.setSensorId("123");
        dto.getMetrics().put("temperature", 72.0);
        dto.getMetrics().put("humidity", 0.65);
        dto.getMetrics().put("wind_speed", 10.0);
        Metric metric = MetricMapper.toEntity(dto);
        assertEquals("123", metric.getSensorId());
        assertEquals(72.0, metric.getTemperature(), 0.001);
        assertEquals(0.65, metric.getHumidity(), 0.001);
        assertEquals(10.0, metric.getWindSpeed(), 0.001);
    }

    @Test
    public void testToEntityWithMissingMetrics() {
        MetricsDto dto = new MetricsDto();
        dto.setSensorId("123");
        dto.getMetrics().put("temperature", 72.0);
        dto.getMetrics().put("humidity", 0.65);
        Metric metric = MetricMapper.toEntity(dto);
        assertEquals("123", metric.getSensorId());
        assertEquals(72.0, metric.getTemperature(), 0.001);
        assertEquals(0.65, metric.getHumidity(), 0.001);
    }
}