package org.scadalts.e2e.pages.dict;

import java.util.stream.Stream;

public enum ChangeType implements ScadaDictionary {

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
