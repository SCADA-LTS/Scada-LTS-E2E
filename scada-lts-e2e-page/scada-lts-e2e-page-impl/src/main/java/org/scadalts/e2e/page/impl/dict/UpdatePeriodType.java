package org.scadalts.e2e.page.impl.dict;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum UpdatePeriodType implements E2eDictionary {

    MILLISECOUND("millisecond(ms)", "8"),
    SECOUND("second(s)", "1"),
    MINUTE("minute(s)", "2"),
    HOUR("hour(s)", "3"),
    NONE("none", "");

    private final String typeName;
    private final String id;

    UpdatePeriodType(String typeName, String id) {
        this.typeName = typeName;
        this.id = id;
    }

    public static UpdatePeriodType getType(String typeName) {
        return Stream.of(UpdatePeriodType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
