package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum LoggingType implements DictionaryObject {

    WHEN_POINT_VALUE_CHANGES("When point value changes"),
    ALL_DATA("All data"),
    DO_NOT_LOG("Do not log"),
    INTERVAL("Interval"),
    WHEN_POINT_TIMESTAMP_CHANGES("When point timestamp changes"),
    NONE("");

    private final String name;

    LoggingType(String name) {
        this.name = name;
    }

    public static LoggingType getType(String typeName) {
        return Stream.of(LoggingType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
