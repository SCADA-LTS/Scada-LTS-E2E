package org.scadalts.e2e.page.impl.dict;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum DataPointType implements E2eDictionary {

    BINARY("Binary"),
    MULTISTATE("Multistate"),
    NUMERIC("Numeric"),
    ALPHANUMERIC("Alphanumeric"),
    NONE("none");

    private String typeName;

    DataPointType(String typeName) {
        this.typeName = typeName;
    }

    public static DataPointType getType(String typeName) {
        return Stream.of(DataPointType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
