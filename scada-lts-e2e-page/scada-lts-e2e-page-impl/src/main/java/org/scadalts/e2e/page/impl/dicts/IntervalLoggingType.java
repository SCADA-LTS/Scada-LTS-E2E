package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum IntervalLoggingType implements DictionaryObject {

    INSTANT("Instant", "1"),
    MAXIMUM("Maximum", "2"),
    MINIMUM("Minimum", "3"),
    AVERAGE("Average", "4");

    private final String name;
    private final String id;

    IntervalLoggingType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static IntervalLoggingType getType(String typeName) {
        return Stream.of(IntervalLoggingType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(INSTANT);
    }

    public static IntervalLoggingType getType(int id) {
        return Stream.of(IntervalLoggingType.values())
                .filter(a -> a.id.equalsIgnoreCase(String.valueOf(id)))
                .findFirst()
                .orElse(INSTANT);
    }
}
