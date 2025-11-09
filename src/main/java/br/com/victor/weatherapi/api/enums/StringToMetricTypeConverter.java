package br.com.victor.weatherapi.api.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToMetricTypeConverter implements Converter<String, MetricType> {

    @Override
    public MetricType convert(String source) {
        return MetricType.fromValue(source);
    }
}