package br.com.victor.weatherapi.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MetricType {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    WINDSPEED("windSpeed");

    private final String value;

    MetricType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MetricType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (MetricType type : MetricType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException(
                "Unknown statistic type: '" + value + "'.");
    }

}
