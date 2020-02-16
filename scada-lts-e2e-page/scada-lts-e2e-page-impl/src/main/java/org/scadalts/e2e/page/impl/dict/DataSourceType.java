package org.scadalts.e2e.page.impl.dict;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum DataSourceType implements E2eDictionary {

    VIRTUAL_DATA_SOURCE("Virtual Data Source", "1"),
    NONE("none", "");

    private final String typeName;
    private final String id;

    DataSourceType(String typeName, String id) {
        this.typeName = typeName;
        this.id = id;
    }

    public static DataSourceType getType(String typeName) {
        return Stream.of(DataSourceType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
