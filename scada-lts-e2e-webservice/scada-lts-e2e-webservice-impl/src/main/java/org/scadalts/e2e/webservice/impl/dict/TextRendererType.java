package org.scadalts.e2e.webservice.impl.dict;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.scadalts.e2e.common.dict.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum TextRendererType implements E2eDictionary {

    PLAIN("textRendererPlain");

    private String typeName;

    TextRendererType(String typeName) {
        this.typeName = typeName;
    }

    @JsonCreator
    public static TextRendererType fromString(String string) {
        return Stream.of(TextRendererType.values())
                .filter(a -> a.typeName.equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
