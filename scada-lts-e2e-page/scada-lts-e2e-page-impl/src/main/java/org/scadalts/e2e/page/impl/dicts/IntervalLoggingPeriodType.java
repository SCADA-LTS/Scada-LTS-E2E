package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.checkerframework.checker.units.qual.N;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum IntervalLoggingPeriodType implements DictionaryObject {

    SECONDS("second(s)", "1"),
    MINUTES(PeriodType.MINUTES),
    HOURS(PeriodType.HOURS),
    DAYS(PeriodType.DAYS),
    WEEKS(PeriodType.WEEKS),
    MONTHS(PeriodType.MONTHS),
    YEARS("year(s)", "7"),
    NONE("none","-1");

    private final String name;
    private final String id;


    IntervalLoggingPeriodType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    IntervalLoggingPeriodType(PeriodType name) {
        this.name = name.getName();
        this.id = name.getId();
    }

    private static IntervalLoggingPeriodType getTypeByName(String typeName) {
        return Stream.of(IntervalLoggingPeriodType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static IntervalLoggingPeriodType getType(int id) {
        return Stream.of(IntervalLoggingPeriodType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static IntervalLoggingPeriodType getType(String id) {
        IntervalLoggingPeriodType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}
