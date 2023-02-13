package br.com.victor.weatherapi.api;


import br.com.victor.weatherapi.api.dto.MetricsDto;
import br.com.victor.weatherapi.api.dto.MetricsStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag(name = "Weather Metrics API", description = "Operations to receive and query weather metrics")
@RequestMapping("weathers")
public interface Controller {

    @PostMapping
    @Operation(summary = "Add a new metric", responses = {
            @ApiResponse(
                    responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = MetricsDto.class))),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request"
            )})
    public ResponseEntity<MetricsDto> addMetric(@RequestBody MetricsDto metric) throws IllegalAccessException;

    @GetMapping
    public ResponseEntity<MetricsStatisticsDto> getMetrics(@RequestParam(required = false) List<String> sensorIds,
                                                           @RequestParam(defaultValue = "temperature,humidity,windSpeed") List<String> metrics,
                                                           @RequestParam(defaultValue = "average") String statistic,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate);
}
