package br.com.victor.weatherapi.api;


import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import br.com.victor.weatherapi.api.enums.StatisticType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Weather Metrics API", description = "Operations to receive and query weather metrics")
@RequestMapping("weather-metrics")
public interface Controller {

    @PostMapping
    @Operation(summary = "Add a new metric", responses = {
            @ApiResponse(
                    responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = SensorMetricDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request"
            )})
    public ResponseEntity<SensorMetricDto> createMetric(@RequestBody SensorMetricDto metric) throws IllegalAccessException;

    @GetMapping
    @Operation(summary = "Get metrics with statistics", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    public ResponseEntity<MetricsStatisticsDto> getMetrics(
            @Parameter(description = "List of sensor IDs to filter by")
            @RequestParam(required = false) List<String> sensorIds,

            @Parameter(description = "Metrics to include", examples = @ExampleObject(value = "[\"temperature\",\"humidity\",\"windSpeed\"]"))
            @RequestParam(defaultValue = "temperature,humidity") List<MetricType> metrics,

            @Parameter(description = "Statistic to calculate",
                    schema = @Schema(
                            implementation = StatisticType.class,
                            allowableValues = {"average", "max", "min"},
                            defaultValue = "average",
                            example = "average"
                    ))
            @RequestParam(defaultValue = "average") StatisticType statistic,

            @Parameter(description = "Start date for filtering (ISO format)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @Parameter(description = "End date for filtering (ISO format)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate);
}
