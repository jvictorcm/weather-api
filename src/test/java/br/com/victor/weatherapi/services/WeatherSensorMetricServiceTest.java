package br.com.victor.weatherapi.services;

import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.enums.StatisticType;
import br.com.victor.weatherapi.model.SensorMetric;
import br.com.victor.weatherapi.repositories.SensorMetricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherSensorMetricServiceTest {

    private SensorMetricService metricsService;

    @Mock
    private SensorMetricRepository metricRepository;


    private static List<SensorMetric> sensorMetrics;

    @BeforeEach
    public void setUp() {
        metricsService = new SensorMetricService(metricRepository);

        SensorMetric firstSensorMetric = new SensorMetric();
        firstSensorMetric.setSensorId("1");
        firstSensorMetric.setWindSpeed(1.0);
        firstSensorMetric.setHumidity(2.0);
        firstSensorMetric.setTemperature(3.0);

        SensorMetric firstSensorMetric2 = new SensorMetric();
        firstSensorMetric2.setSensorId("1");
        firstSensorMetric2.setWindSpeed(2.0);
        firstSensorMetric2.setHumidity(3.0);
        firstSensorMetric2.setTemperature(4.0);

        SensorMetric firstSensorMetric3 = new SensorMetric();
        firstSensorMetric3.setSensorId("1");
        firstSensorMetric3.setWindSpeed(10.0);
        firstSensorMetric3.setHumidity(10.0);
        firstSensorMetric3.setTemperature(10.0);

        sensorMetrics = Arrays.asList(firstSensorMetric, firstSensorMetric3, firstSensorMetric2);
    }

    @Test
    public void testGetMetricsAverage() {
        when(metricRepository.findByParameters(Arrays.asList("1", "2"), null, null))
                .thenReturn(sensorMetrics);
//        when(metricGrouper.groupBySensorId(sensorMetrics)).thenReturn(null);
        MetricsStatisticsDto response = metricsService.getMetrics(Arrays.asList("1", "2"), Arrays.asList(MetricType.WINDSPEED, MetricType.HUMIDITY, MetricType.TEMPERATURE), StatisticType.AVERAGE, null, null);

        assertAll("Statistics response validation",
                () -> assertEquals(3, response.getMetricsList().size(),
                        "Should return 3 metrics in the list"),

                () -> assertEquals(4.3, response.getStatistics().get(0).getMetrics().get(MetricType.WINDSPEED), 0.1,
                        "Wind speed statistic should be 4.3"),

                () -> assertEquals(5, response.getStatistics().get(0).getMetrics().get(MetricType.HUMIDITY), 0.1,
                        "Humidity statistic should be 5"),

                () -> assertEquals(5.6, response.getStatistics().get(0).getMetrics().get(MetricType.TEMPERATURE), 0.1,
                        "Temperature statistic should be 5.6")
        );
    }

    @Test
    public void testGetMetricsMax() {
        when(metricRepository.findByParameters(Arrays.asList("1", "2"), null, null))
                .thenReturn(sensorMetrics);

        MetricsStatisticsDto response = metricsService.getMetrics(Arrays.asList("1", "2"), Arrays.asList(MetricType.WINDSPEED, MetricType.HUMIDITY, MetricType.TEMPERATURE), StatisticType.MAX, null, null);

        assertAll("Statistics response validation",
                () -> assertEquals(3, response.getMetricsList().size(),
                        "Should return 3 metrics in the list"),

                () -> assertEquals(10, response.getStatistics().get(0).getMetrics().get(MetricType.WINDSPEED), 0.1,
                        "Wind speed statistic should be 10"),

                () -> assertEquals(10, response.getStatistics().get(0).getMetrics().get(MetricType.HUMIDITY), 0.1,
                        "Humidity statistic should be 10"),

                () -> assertEquals(10, response.getStatistics().get(0).getMetrics().get(MetricType.TEMPERATURE), 0.1,
                        "Temperature statistic should be 10")
        );
    }

    @Test
    public void testGetMetricsMin() {
        when(metricRepository.findByParameters(Arrays.asList("1", "2"), null, null))
                .thenReturn(sensorMetrics);

        MetricsStatisticsDto response = metricsService.getMetrics(Arrays.asList("1", "2"), Arrays.asList(MetricType.WINDSPEED, MetricType.HUMIDITY, MetricType.TEMPERATURE), StatisticType.MIN, null, null);

        assertAll("Statistics response validation",
                () -> assertEquals(3, response.getMetricsList().size(),
                        "Should return 3 metrics in the list"),

                () -> assertEquals(1, response.getStatistics().get(0).getMetrics().get(MetricType.WINDSPEED), 0.1,
                        "Wind speed statistic should be 1"),

                () -> assertEquals(2, response.getStatistics().get(0).getMetrics().get(MetricType.HUMIDITY), 0.1,
                        "Humidity statistic should be 2"),

                () -> assertEquals(3, response.getStatistics().get(0).getMetrics().get(MetricType.TEMPERATURE), 0.1,
                        "Temperature statistic should be 3")
        );
    }
}