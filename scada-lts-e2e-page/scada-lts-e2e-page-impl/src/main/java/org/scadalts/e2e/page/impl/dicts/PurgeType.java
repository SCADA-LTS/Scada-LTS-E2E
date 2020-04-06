package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum PurgeType implements DictionaryObject {

    DAY_S("day(s)"),
    WEEK_S("week(s)"),
    MONTH_S("month(s)"),
    YEAR_S("year(s)");

    private final String name;

    PurgeType(String name) {
        this.name = name;
    }

    public static PurgeType getType(String typeName) {
        return Stream.of(PurgeType.values())
                .filter(a -> a.name.equals(typeName))
                .findFirst()
                .orElse(YEAR_S);
    }
}
