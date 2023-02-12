package br.com.victor.weatherapi.api;


import br.com.victor.weatherapi.api.dto.MetricsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Weather Metrics API", description = "Operations to receive and query weather metrics")
@RequestMapping("weathers")
public interface Controller {

    @PostMapping
    @Operation(summary = "Add a new metric", responses = {@ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = MetricsDto.class)))})
    public ResponseEntity<MetricsDto> addMetric(@RequestBody MetricsDto metric);
}
