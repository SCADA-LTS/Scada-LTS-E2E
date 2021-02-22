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

    public static EventHandlerType getType(String typeName) {
        return Stream.of(EventHandlerType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }
}

