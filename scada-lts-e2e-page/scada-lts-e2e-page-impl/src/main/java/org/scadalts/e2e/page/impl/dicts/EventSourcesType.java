package org.scadalts.e2e.page.impl.dicts;

import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

public enum EventSourcesType implements DictionaryObject {
    DATA_POINT("Data point", "1"),
    DATA_SOURCE("Data source", "3"),
    SYSTEM("System", "4"),
    COMPOUND("Compound", "5"),
    SCHEDULED("Scheduled", "6"),
    PUBLISHER("Publisher", "7"),
    AUDIT("Audit", "8"),
    MAINTENANCE("Maintenance", "9"),
    NONE("", "");

    private final String name;
    private final String id;

    EventSourcesType(String name, String id) {
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

    private static EventSourcesType getTypeByName(String typeName) {
        return Stream.of(EventSourcesType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static EventSourcesType getType(int id) {
        return Stream.of(EventSourcesType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static EventSourcesType getType(String id) {
        EventSourcesType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}
