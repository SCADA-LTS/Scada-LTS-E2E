package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum ChartRendererType implements DictionaryObject {

    NONE("None", "chartRendererNone"),
    TABLE("Table", "chartRendererTable"),
    IMAGE("Image", "chartRendererImage"),
    STATS("Statistics", "chartRendererStats");

    private final String name;
    private final String id;

    ChartRendererType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static ChartRendererType getType(String typeName) {
        return Stream.of(ChartRendererType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName) || a.getId().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
