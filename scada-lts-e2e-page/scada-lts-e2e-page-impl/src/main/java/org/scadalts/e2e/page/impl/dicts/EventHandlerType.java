package org.scadalts.e2e.page.impl.dicts;

import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

public enum EventHandlerType implements DictionaryObject {

    EMAIL("Email", "2"),
    SET_POINT("Set point", "1"),
    PROCESS("Process", "3"),
    SCRIPT("Script", "4"),
    SMS("Sms", "5"),
    NONE("", "");

    private final String name;
    private final String id;

    EventHandlerType(String name, String id) {
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

    private static EventHandlerType getTypeByName(String typeName) {
        return Stream.of(EventHandlerType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static EventHandlerType getType(int id) {
        return Stream.of(EventHandlerType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static EventHandlerType getType(String id) {
        EventHandlerType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}

