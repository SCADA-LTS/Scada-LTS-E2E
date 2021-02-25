package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum  TextRendererType implements DictionaryObject {
    ANALOG("Analog"),
    PLAIN("Plain"),
    RANGE("Range"),
    TIME("Time");

    private final String name;

    TextRendererType(String name) {
        this.name = name;
    }

    public static TextRendererType getType(String typeName) {
        return Stream.of(TextRendererType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(PLAIN);
    }
}
