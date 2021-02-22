package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum IntervalLoggingType implements DictionaryObject {

    INSTANT("Instant"),
    MAXIMUM("Maximum"),
    MINIMUM("Minimum"),
    AVERAGE("Average");

    private final String name;

    IntervalLoggingType(String name) {
        this.name = name;
    }

    public static IntervalLoggingType getType(String typeName) {
        return Stream.of(IntervalLoggingType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(INSTANT);
    }
}
