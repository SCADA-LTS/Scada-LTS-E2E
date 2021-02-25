package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum DataPointType implements DictionaryObject {

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
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
