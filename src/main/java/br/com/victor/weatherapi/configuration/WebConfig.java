package br.com.victor.weatherapi.configuration;

import br.com.victor.weatherapi.api.enums.StringToMetricTypeConverter;
import br.com.victor.weatherapi.api.enums.StringToStatisticTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToStatisticTypeConverter());
        registry.addConverter(new StringToMetricTypeConverter());
    }
}