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
    MONTHS("month(s)", "6"),
    YEARS("year(s)", "7"),
    NONE("none", "8");

    private final String name;
    private final String id;

    PeriodType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    private static PeriodType getTypeByName(String typeName) {
        return Stream.of(PeriodType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static PeriodType getType(int id) {
        return Stream.of(PeriodType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static PeriodType getType(String id) {
        PeriodType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}
