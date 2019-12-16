package org.scadalts.e2e.pages.dict;

import java.util.stream.Stream;

public enum DataSourceType implements ScadaDictionary {

    VIRTUAL_DATA_SOURCE("Virtual Data Source"),
    NONE("none");

    private String typeName;

    DataSourceType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public static DataSourceType getType(String typeName) {
        return Stream.of(DataSourceType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
