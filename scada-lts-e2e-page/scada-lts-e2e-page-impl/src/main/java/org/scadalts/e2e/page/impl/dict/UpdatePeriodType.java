package org.scadalts.e2e.page.impl.dict;

import org.scadalts.e2e.common.dict.E2eDictionary;

import java.util.stream.Stream;

public enum UpdatePeriodType implements E2eDictionary {

    MILLISECOUND("millisecond(ms)"),
    SECOUND("second(s)"),
    MINUTE("minute(s)"),
    HOUR("hour(s)"),
    NONE("none");

    private String typeName;

    UpdatePeriodType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public static UpdatePeriodType getType(String typeName) {
        return Stream.of(UpdatePeriodType.values())
                .filter(a -> a.typeName.equals(typeName))
                .findFirst()
                .orElse(NONE);
    }
}
