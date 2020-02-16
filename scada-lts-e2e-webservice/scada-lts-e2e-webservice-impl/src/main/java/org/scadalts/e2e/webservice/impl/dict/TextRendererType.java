package org.scadalts.e2e.webservice.impl.dict;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum TextRendererType implements E2eDictionary {

    PLAIN("textRendererPlain", "textRendererPlain");

    private final String typeName;
    private final String id;

    TextRendererType(String typeName, String id) {
        this.typeName = typeName;
        this.id = id;
    }

    @JsonCreator
    public static TextRendererType fromString(String string) {
        return Stream.of(TextRendererType.values())
                .filter(a -> a.typeName.equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
