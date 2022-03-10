package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum DataPointType implements DictionaryObject {

    BINARY("Binary", "Boolean", "0"),
    MULTISTATE("Multistate", "Multistate", ""),
    NUMERIC("Numeric", "Analog", "0.0"),
    ALPHANUMERIC("Alphanumeric", "", "abc"),
    NONE("none", "", "");

    private final String name;
    private final String id;
    private final String defaultValue;

    DataPointType(String name, String id, String defaultValue) {
        this.name = name;
        this.id = id;
        this.defaultValue = defaultValue;
    }

    public static DataPointType getType(String typeName) {
        return Stream.of(DataPointType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
