package org.scadalts.e2e.page.impl.dicts;

import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

public enum EventType implements DictionaryObject {
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

    private static EventType getTypeByName(String typeName) {
        return Stream.of(EventType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static EventType getType(int id) {
        return Stream.of(EventType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static EventType getType(String id) {
        EventType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}
