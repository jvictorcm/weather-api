package br.com.victor.weatherapi.api.validation;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;

public abstract class AbstractCreateSensorMetricValidator implements CreateSensorMetricValidator {
    protected CreateSensorMetricValidator next;

    @Override
    public void setNext(CreateSensorMetricValidator next) {
        this.next = next;
    }

    protected ValidationResult validateNext(SensorMetricDto sensorMetricDto) {
        if (next != null) {
            return next.validate(sensorMetricDto);
        }
        return ValidationResult.valid();
    }
}
