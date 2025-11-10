package br.com.victor.weatherapi.api.validation;

import br.com.victor.weatherapi.api.dto.SensorMetricDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateSensorMetricValidationChain {
    private final SensorIdFormatValidator sensorIdFormatValidator;
    private final SensorMetricValidator sensorMetricValidator;

    @Autowired
    public CreateSensorMetricValidationChain(SensorIdFormatValidator sensorIdFormatValidator, SensorMetricValidator sensorMetricValidator) {
        this.sensorIdFormatValidator = sensorIdFormatValidator;
        this.sensorMetricValidator = sensorMetricValidator;
    }

    public CreateSensorMetricValidator buildChain() {
        //building the chain here
        sensorIdFormatValidator.setNext(sensorMetricValidator);
//        sensorMetricValidator.setNext(someExtraValidatorHere);
        return sensorIdFormatValidator;
    }

    public ValidationResult validate(SensorMetricDto metric) {
        CreateSensorMetricValidator chain = buildChain();
        return chain.validate(metric);
    }
}
