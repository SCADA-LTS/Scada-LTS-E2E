package org.scadalts.e2e.service.impl.dicts;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum TextRendererType implements E2eDictionary {

    PLAIN("textRendererPlain", "textRendererPlain");

    private final String name;
    private final String id;

    TextRendererType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @JsonCreator
    public static TextRendererType fromString(String string) {
        return Stream.of(TextRendererType.values())
                .filter(a -> a.name.equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
