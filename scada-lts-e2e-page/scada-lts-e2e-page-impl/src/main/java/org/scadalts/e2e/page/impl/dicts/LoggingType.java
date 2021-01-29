package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum LoggingType implements DictionaryObject {

    ON_CHANGE("When point value changes"),
    ALL("All data"),
    NONE("Do not log"),
    INTERVAL("Interval"),
    ON_TS_CHANGE("When point timestamp changes");

    private final String name;

    LoggingType(String name) {
        this.name = name;
    }

    public static LoggingType getType(String typeName) {
        return Stream.of(LoggingType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
