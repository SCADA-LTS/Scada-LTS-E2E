package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum AlarmLevel implements DictionaryObject {

    INFORMATION("Information", "1"),
    URGENT("Urgent", "2"),
    CRITICAL("Critical", "3"),
    LIFE_SAFETY("Life safety", "4"),
    NONE("None", "0");

    private final String name;
    private final String id;

    AlarmLevel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static AlarmLevel getTypeByName(String typeName) {
        return Stream.of(AlarmLevel.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static AlarmLevel getType(String id) {
        return Stream.of(AlarmLevel.values())
                .filter(a -> a.id.equalsIgnoreCase(id))
                .findFirst()
                .orElse(NONE);
    }
}