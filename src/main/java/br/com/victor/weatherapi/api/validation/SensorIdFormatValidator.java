package br.com.victor.weatherapi.api.validation;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import org.springframework.stereotype.Component;

@Component
public class SensorIdFormatValidator extends AbstractCreateSensorMetricValidator {

    @Override
    public ValidationResult validate(SensorMetricDto metric) {
        if (metric.getSensorId() == null || metric.getSensorId().trim().isEmpty()) {
            return ValidationResult.invalid("Invalid Sensor ID. Cannot be null or empty");
        }
        if (metric.getSensorId().length() > 50) {
            return ValidationResult.invalid("Sensor ID length cant be greater than 50 characters.");
        }
        //here we could lay out more types of validation.
        return validateNext(metric);
    }
}
