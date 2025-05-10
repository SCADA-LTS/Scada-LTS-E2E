package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum LoggingType implements DictionaryObject {

    ON_CHANGE("When point value changes", "1"),
    ALL("All data", "2"),
    NONE("Do not log", "3"),
    INTERVAL("Interval", "4"),
    ON_TS_CHANGE("When point timestamp changes", "5");

    private final String name;
    private final String id;

    LoggingType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static LoggingType getType(String typeName) {
        return Stream.of(LoggingType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static LoggingType getType(int id) {
        return Stream.of(LoggingType.values())
                .filter(a -> a.id.equalsIgnoreCase(String.valueOf(id)))
                .findFirst()
                .orElse(NONE);
    }
}
