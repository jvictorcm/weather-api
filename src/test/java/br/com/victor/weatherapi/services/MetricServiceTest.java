package br.com.victor.weatherapi.services;

import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.enums.StatisticType;
import br.com.victor.weatherapi.model.Metric;
import br.com.victor.weatherapi.repositories.MetricRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetricServiceTest {

    @InjectMocks
    private MetricService metricsService;

    @Mock
    private MetricRepository metricRepository;

    private static List<Metric> metrics;

    @BeforeAll
    public static void setUp() {
        Metric firstMetric = new Metric();
        firstMetric.setSensorId("1");
        firstMetric.setWindSpeed(1.0);
        firstMetric.setHumidity(2.0);
        firstMetric.setTemperature(3.0);

        Metric firstMetric2 = new Metric();
        firstMetric2.setSensorId("1");
        firstMetric2.setWindSpeed(2.0);
        firstMetric2.setHumidity(3.0);
        firstMetric2.setTemperature(4.0);

        Metric firstMetric3 = new Metric();
        firstMetric3.setSensorId("1");
        firstMetric3.setWindSpeed(10.0);
        firstMetric3.setHumidity(10.0);
        firstMetric3.setTemperature(10.0);

        metrics = Arrays.asList(firstMetric, firstMetric3, firstMetric2);
    }

    @Test
    public void testGetMetricsAverage() {
        when(metricRepository.findByParameters(Arrays.asList("1", "2"), null, null))
                .thenReturn(metrics);

        MetricsStatisticsDto response = metricsService.getMetrics(Arrays.asList("1", "2"), Arrays.asList(MetricType.WINDSPEED, MetricType.HUMIDITY, MetricType.TEMPERATURE), StatisticType.AVERAGE, null, null);

        assertAll("Statistics response validation",
                () -> assertEquals(3, response.getMetricsList().size(),
                        "Should return 3 metrics in the list"),

                () -> assertEquals(4.3, response.getStatistics().get(0).getMetrics().get("windSpeed"), 0.1,
                        "Wind speed statistic should be 4.3"),

                () -> assertEquals(5, response.getStatistics().get(0).getMetrics().get("humidity"), 0.1,
                        "Humidity statistic should be 5"),

                () -> assertEquals(5.6, response.getStatistics().get(0).getMetrics().get("temperature"), 0.1,
                        "Temperature statistic should be 5.6")
        );
    }

    @Test
    public void testGetMetricsMax() {
        when(metricRepository.findByParameters(Arrays.asList("1", "2"), null, null))
                .thenReturn(metrics);

        MetricsStatisticsDto response = metricsService.getMetrics(Arrays.asList("1", "2"), Arrays.asList(MetricType.WINDSPEED, MetricType.HUMIDITY, MetricType.TEMPERATURE), StatisticType.MAX, null, null);

        assertAll("Statistics response validation",
                () -> assertEquals(3, response.getMetricsList().size(),
                        "Should return 3 metrics in the list"),

                () -> assertEquals(0, response.getStatistics().get(0).getMetrics().get("windSpeed"), 0.1,
                        "Wind speed statistic should be 0"),

                () -> assertEquals(0, response.getStatistics().get(0).getMetrics().get("humidity"), 0.1,
                        "Humidity statistic should be 0"),

                () -> assertEquals(0, response.getStatistics().get(0).getMetrics().get("temperature"), 0.1,
                        "Temperature statistic should be 0")
        );
    }

    @Test
    public void testGetMetricsMin() {
        when(metricRepository.findByParameters(Arrays.asList("1", "2"), null, null))
                .thenReturn(metrics);

        MetricsStatisticsDto response = metricsService.getMetrics(Arrays.asList("1", "2"), Arrays.asList(MetricType.WINDSPEED, MetricType.HUMIDITY, MetricType.TEMPERATURE), StatisticType.MIN, null, null);

        assertAll("Statistics response validation",
                () -> assertEquals(3, response.getMetricsList().size(),
                        "Should return 3 metrics in the list"),

                () -> assertEquals(1, response.getStatistics().get(0).getMetrics().get("windSpeed"), 0.1,
                        "Wind speed statistic should be 1"),

                () -> assertEquals(2, response.getStatistics().get(0).getMetrics().get("humidity"), 0.1,
                        "Humidity statistic should be 2"),

                () -> assertEquals(3, response.getStatistics().get(0).getMetrics().get("temperature"), 0.1,
                        "Temperature statistic should be 3")
        );
    }
}