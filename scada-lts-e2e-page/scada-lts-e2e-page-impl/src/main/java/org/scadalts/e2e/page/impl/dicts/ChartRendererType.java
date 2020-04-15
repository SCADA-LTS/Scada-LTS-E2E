package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum ChartRendererType implements DictionaryObject {

    NONE("None"),
    TABLE("Table"),
    IMAGE("Image"),
    STATS("Statistics");

    private final String name;

    ChartRendererType(String name) {
        this.name = name;
    }

    public static ChartRendererType getType(String typeName) {
        return Stream.of(ChartRendererType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
