package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum PeriodType implements DictionaryObject {

    MINUTES("minute(s)", "2"),
    HOURS("hour(s)", "3"),
    DAYS("day(s)", "4"),
    WEEKS("week(s)", "5"),
    MONTHS("month(s)", "6");

    private final String name;
    private final String id;

    PeriodType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static PeriodType getType(String typeName) {
        return Stream.of(PeriodType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(MINUTES);
    }

    public static PeriodType getType(int id) {
        return Stream.of(PeriodType.values())
                .filter(a -> a.id.equalsIgnoreCase(String.valueOf(id)))
                .findFirst()
                .orElse(MINUTES);
    }
}
