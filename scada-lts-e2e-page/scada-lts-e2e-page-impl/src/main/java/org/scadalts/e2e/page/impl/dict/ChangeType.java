package org.scadalts.e2e.page.impl.dict;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum ChangeType implements E2eDictionary {

    ALTERNATE("Alternate", "alternate"),
    RANDOM("Random", "random"),
    NO_CHANGE("No change", "noChange"),
    INCREMENT("Increment", "increment"),
    BROWNIAN("Brownian", "brownian"),
    ATTRACTOR("Attractor", "attractor"),
    NONE("none", "");

    private final String typeName;
    private final String id;

    ChangeType(String typeName, String id) {
        this.typeName = typeName;
        this.id = id;
    }

    public static ChangeType getType(String typeName) {
        return Stream.of(ChangeType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
