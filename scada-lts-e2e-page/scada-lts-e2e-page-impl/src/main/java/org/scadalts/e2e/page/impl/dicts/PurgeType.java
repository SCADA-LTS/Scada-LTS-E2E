package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum PurgeType implements DictionaryObject {

    DAYS(PeriodType.DAYS),
    WEEKS(PeriodType.WEEKS),
    MONTHS(PeriodType.MONTHS),
    YEARS(PeriodType.YEARS);

    private final String name;
    private final String id;

    PurgeType(PeriodType name) {
        this.name = name.getName();
        this.id = name.getId();
    }

    public static PurgeType getType(String typeName) {
        return Stream.of(PurgeType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(YEARS);
    }

    public static PurgeType getType(int id) {
        return Stream.of(PurgeType.values())
                .filter(a -> a.id.equalsIgnoreCase(String.valueOf(id)))
                .findFirst()
                .orElse(YEARS);
    }
}
