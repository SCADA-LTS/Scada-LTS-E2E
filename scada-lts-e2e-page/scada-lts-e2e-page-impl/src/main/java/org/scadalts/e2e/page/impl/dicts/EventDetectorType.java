package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum EventDetectorType implements DictionaryObject {

    HIGH_LIMIT("High limit", "HIGH_LIMIT", "1"),
    LOW_LIMIT("Low limit", "LOW_LIMIT", "2"),
    CHANGE("Change", "POINT_CHANGE", "5"),
    NO_CHANGE("No change", "NO_CHANGE", "7"),
    NO_UPDATE("No update", "NO_UPDATE", "8"),
    POSITIVE_CUSUM("Positive CUSUM", "POSITIVE_CUSUM", "10"),
    NEGATIVE_CUSUM("Negative CUSUM", "NEGATIVE_CUSUM", "11"),
    NONE("none", "", "");

    private final String name;
    private final String alternateName;
    private final String id;


    EventDetectorType(String name, String alternateName, String id) {
        this.name = name;
        this.alternateName = alternateName;
        this.id = id;
    }

    public static EventDetectorType getType(String typeName) {
        return Stream.of(EventDetectorType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName)
                        || a.alternateName.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static EventDetectorType getTypeContains(String typeName) {
        return Stream.of(EventDetectorType.values())
                .filter(a -> typeName.contains(a.name)
                        || a.alternateName.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
