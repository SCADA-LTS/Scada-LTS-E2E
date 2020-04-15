package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum PurgeType implements DictionaryObject {

    DAYS("day(s)"),
    WEEKS("week(s)"),
    MONTHS("month(s)"),
    YEARS("year(s)");

    private final String name;

    PurgeType(String name) {
        this.name = name;
    }

    public static PurgeType getType(String typeName) {
        return Stream.of(PurgeType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(YEARS);
    }
}
