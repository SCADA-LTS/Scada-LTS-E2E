package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum ChangeType implements DictionaryObject {

    ALTERNATE("Alternate", "alternate"),
    RANDOM("Random", "random"),
    NO_CHANGE("No change", "noChange"),
    INCREMENT("Increment", "increment"),
    BROWNIAN("Brownian", "brownian"),
    ATTRACTOR("Attractor", "attractor"),
    NONE("none", "");

    private final String name;
    private final String id;

    ChangeType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static ChangeType getType(String typeName) {
        return Stream.of(ChangeType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
