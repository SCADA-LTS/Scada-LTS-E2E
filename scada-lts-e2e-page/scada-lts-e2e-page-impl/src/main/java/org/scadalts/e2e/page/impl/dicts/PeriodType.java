package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum PeriodType implements DictionaryObject {

    MINUTES("minute(s)"),
    HOURS("hour(s)"),
    DAYS("day(s)"),
    WEEKS("week(s)"),
    MONTHS("month(s)");

    private final String name;

    PeriodType(String name) {
        this.name = name;
    }

    public static PeriodType getType(String typeName) {
        return Stream.of(PeriodType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(MINUTES);
    }
}
