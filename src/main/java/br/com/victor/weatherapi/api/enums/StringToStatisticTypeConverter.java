package br.com.victor.weatherapi.api.enums;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class StringToStatisticTypeConverter implements Converter<String, StatisticType> {

    @Override
    public StatisticType convert(String source) {
        return StatisticType.fromValue(source);
    }
}