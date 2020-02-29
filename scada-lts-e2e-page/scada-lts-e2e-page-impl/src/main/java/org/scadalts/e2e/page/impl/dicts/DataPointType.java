package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum DataPointType implements E2eDictionary {

    BINARY("Binary", "Boolean"),
    MULTISTATE("Multistate", "Multistate"),
    NUMERIC("Numeric", "Analog"),
    ALPHANUMERIC("Alphanumeric", ""),
    NONE("none", "");

    private final String name;
    private final String id;

    DataPointType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static DataPointType getType(String typeName) {
        return Stream.of(DataPointType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
