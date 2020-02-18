package org.scadalts.e2e.page.impl.dict;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum DataSourceType implements E2eDictionary {

    VIRTUAL_DATA_SOURCE("Virtual Data Source", "1"),
    NONE("none", "");

    private final String name;
    private final String id;

    DataSourceType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static DataSourceType getType(String typeName) {
        return Stream.of(DataSourceType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
