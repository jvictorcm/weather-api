package br.com.victor.weatherapi.api.validation;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;

public interface CreateSensorMetricValidator {
    ValidationResult validate(SensorMetricDto metric);

    void setNext(CreateSensorMetricValidator next);
}