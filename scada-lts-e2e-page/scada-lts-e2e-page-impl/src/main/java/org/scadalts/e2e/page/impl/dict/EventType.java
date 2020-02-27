package org.scadalts.e2e.page.impl.dict;

import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

public enum EventType implements E2eDictionary {
    CHANGE("Change", "2"),
    UPDATE("Update", "1"),
    NONE("", "");

    private final String name;
    private final String id;

    EventType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    public static EventType getType(String typeName) {
        return Stream.of(EventType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
