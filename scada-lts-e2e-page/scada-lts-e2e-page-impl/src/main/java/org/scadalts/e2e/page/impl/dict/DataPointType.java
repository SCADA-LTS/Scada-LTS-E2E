package org.scadalts.e2e.page.impl.dict;

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

    private final String typeName;
    private final String id;

    DataPointType(String typeName, String id) {
        this.typeName = typeName;
        this.id = id;
    }

    public static DataPointType getType(String typeName) {
        return Stream.of(DataPointType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
