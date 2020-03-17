package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum EventDetectorType implements DictionaryObject {

    HIGH_LIMIT("High limit", "1"),
    LOW_LIMIT("Low limit", "2"),
    CHANGE("Change", "5"),
    NO_CHANGE("No change", "7"),
    NO_UPDATE("No update", "8"),
    POSITIVE_CUSUM("Positive CUSUM", "10"),
    NEGATIVE_CUSUM("Negative CUSUM", "11"),
    NONE("none", "");

    private final String name;
    private final String id;

    EventDetectorType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static EventDetectorType getType(String typeName) {
        return Stream.of(EventDetectorType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static EventDetectorType getTypeContains(String typeName) {
        return Stream.of(EventDetectorType.values())
                .filter(a -> typeName.contains(a.name))
                .findFirst()
                .orElse(NONE);
    }
}
