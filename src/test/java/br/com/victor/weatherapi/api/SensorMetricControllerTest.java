package br.com.victor.weatherapi.api;

import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.impl.SensorMetricController;
import br.com.victor.weatherapi.services.SensorMetricService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorMetricController.class)
class SensorMetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorMetricService sensorMetricService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateSensorMetric_withoutHumidity() throws Exception {
        String filePath = "createSensorWithoutHumidity.json";
        SensorMetricDto metric = objectMapper.readValue(
                new ClassPathResource(filePath).getFile(), SensorMetricDto.class);

        when(sensorMetricService.addMetric(any(SensorMetricDto.class))).thenReturn(metric);

        mockMvc.perform(post("/weather-metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metric)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorId").value(metric.getSensorId()))
                .andExpect(jsonPath("$.metrics.temperature").value(metric.getMetrics().get(MetricType.TEMPERATURE)))
                .andExpect(jsonPath("$.metrics.windSpeed").value(metric.getMetrics().get(MetricType.WINDSPEED)))
                .andExpect(jsonPath("$.metrics.humidity").doesNotExist());
    }

    @Test
    void testCreateSensorMetric_complete() throws Exception {
        String filePath = "createSensor.json";
        SensorMetricDto metric = objectMapper.readValue(
                new ClassPathResource(filePath).getFile(), SensorMetricDto.class);

        when(sensorMetricService.addMetric(any(SensorMetricDto.class))).thenReturn(metric);

        mockMvc.perform(post("/weather-metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metric)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorId").value(metric.getSensorId()))
                .andExpect(jsonPath("$.metrics.temperature").value(metric.getMetrics().get(MetricType.TEMPERATURE)))
                .andExpect(jsonPath("$.metrics.windSpeed").value(metric.getMetrics().get(MetricType.WINDSPEED)))
                .andExpect(jsonPath("$.metrics.humidity").value(metric.getMetrics().get(MetricType.HUMIDITY)));
    }

    @Test
    void testGetSensorMetric() throws Exception {
        String filePath = "getSensorData.json";
        MetricsStatisticsDto metricResponse = objectMapper.readValue(
                new ClassPathResource(filePath).getFile(), MetricsStatisticsDto.class);

        when(sensorMetricService.getMetrics(any(), any(), any(), any(), any())).thenReturn(metricResponse);

        mockMvc.perform(get("/weather-metrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metricsList").exists())
                .andExpect(jsonPath("$.metricsList").isArray())
                .andExpect(jsonPath("$.metricsList").isNotEmpty())
                .andExpect(jsonPath("$.statistics").exists())
                .andExpect(jsonPath("$.statistics").isArray())
                .andExpect(jsonPath("$.statistics").isNotEmpty());
    }
}