package org.scadalts.e2e.pages.dict;

import java.util.stream.Stream;

public enum DataPointType implements ScadaDictionary {

    BINARY("Binary"),
    MULTISTATE("Multistate"),
    NUMERIC("Numeric"),
    ALPHANUMERIC("Alphanumeric"),
    NONE("none");

    private String typeName;

    DataPointType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public static DataPointType getType(String typeName) {
        return Stream.of(DataPointType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
