package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum UpdatePeriodType implements E2eDictionary {

    MILLISECOUND("millisecond(ms)", "8"),
    SECOND("second(s)", "1"),
    MINUTE("minute(s)", "2"),
    HOUR("hour(s)", "3"),
    NONE("none", "");

    private final String name;
    private final String id;

    UpdatePeriodType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static UpdatePeriodType getType(String typeName) {
        return Stream.of(UpdatePeriodType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
