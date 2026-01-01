package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum UpdatePeriodType implements DictionaryObject {

    MILLISECOND("millisecond(ms)", "8"),
    SECOND("second(s)", "1"),
    MINUTE("minute(s)", "2"),
    HOUR("hour(s)", "3"),
    NONE("none", ""),
    ANY("","");

    private final String name;
    private final String id;

    UpdatePeriodType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    private static UpdatePeriodType getTypeByName(String typeName) {
        return Stream.of(UpdatePeriodType.values())
                .filter(a -> a.name.equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static UpdatePeriodType getType(int id) {
        return Stream.of(UpdatePeriodType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static UpdatePeriodType getType(String id) {
        UpdatePeriodType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}
