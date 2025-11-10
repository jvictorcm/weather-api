package br.com.victor.weatherapi.api.validation;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import br.com.victor.weatherapi.api.enums.MetricType;
import org.springframework.stereotype.Service;

@Service
public class SensorMetricValidator extends AbstractCreateSensorMetricValidator {

    @Override
    public ValidationResult validate(SensorMetricDto metric) {
        if (metric.getMetrics() == null || metric.getMetrics().isEmpty()) {
            return ValidationResult.invalid("The entry should have at least one metric.");
        }
        //example of specific validation
        for (MetricType type : metric.getMetrics().keySet()) {
            if (type.equals(MetricType.HUMIDITY) && metric.getMetrics().get(MetricType.HUMIDITY) < 0) {
                return ValidationResult.invalid("Humidity cant be a negative value");
            }
        }
        //more validations here
        return validateNext(metric);
    }
}
