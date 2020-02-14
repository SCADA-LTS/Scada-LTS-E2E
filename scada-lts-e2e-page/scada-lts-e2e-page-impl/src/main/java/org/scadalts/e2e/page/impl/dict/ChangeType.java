package org.scadalts.e2e.page.impl.dict;

import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

public enum ChangeType implements E2eDictionary {

    ALTERNATE("Alternate"),
    RANDOM("Random"),
    NO_CHANGE("No change"),
    NONE("none");

    private final String typeName;

    ChangeType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public static ChangeType getType(String typeName) {
        return Stream.of(ChangeType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
