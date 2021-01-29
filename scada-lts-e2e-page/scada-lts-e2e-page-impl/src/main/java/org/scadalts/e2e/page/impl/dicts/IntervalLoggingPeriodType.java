package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum IntervalLoggingPeriodType implements DictionaryObject {

    SECONDS("second(s)"),
    MINUTES(PeriodType.MINUTES),
    HOURS(PeriodType.HOURS),
    DAYS(PeriodType.DAYS),
    WEEKS(PeriodType.WEEKS),
    MONTHS(PeriodType.MONTHS),
    YEARS("year(s)");

    private final String name;

    IntervalLoggingPeriodType(String name) {
        this.name = name;
    }

    IntervalLoggingPeriodType(PeriodType name) {
        this.name = name.getName();
    }

    public static IntervalLoggingPeriodType getType(String typeName) {
        return Stream.of(IntervalLoggingPeriodType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(MINUTES);
    }
}
