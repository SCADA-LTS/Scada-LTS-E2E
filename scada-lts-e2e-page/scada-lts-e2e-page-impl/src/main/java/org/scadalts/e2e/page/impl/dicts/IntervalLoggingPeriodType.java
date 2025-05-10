package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
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
    YEARS("year(s)", "7");

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

    public static IntervalLoggingPeriodType getType(String typeName) {
        return Stream.of(IntervalLoggingPeriodType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(MINUTES);
    }

    public static IntervalLoggingPeriodType getType(int id) {
        return Stream.of(IntervalLoggingPeriodType.values())
                .filter(a -> a.id.equalsIgnoreCase(String.valueOf(id)))
                .findFirst()
                .orElse(MINUTES);
    }
}
