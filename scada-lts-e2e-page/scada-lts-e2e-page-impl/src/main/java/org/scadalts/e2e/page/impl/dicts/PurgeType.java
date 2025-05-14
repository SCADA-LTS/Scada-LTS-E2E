package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum PurgeType implements DictionaryObject {

    DAYS(PeriodType.DAYS),
    WEEKS(PeriodType.WEEKS),
    MONTHS(PeriodType.MONTHS),
    YEARS(PeriodType.YEARS),
    NONE("none", "-1");

    private final String name;
    private final String id;

    PurgeType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    PurgeType(PeriodType name) {
        this.name = name.getName();
        this.id = name.getId();
    }

    private static PurgeType getTypeByName(String typeName) {
        return Stream.of(PurgeType.values())
                .filter(a -> a.name().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(NONE);
    }

    public static PurgeType getType(int id) {
        return Stream.of(PurgeType.values())
                .filter(a -> Integer.parseInt(a.id) == id)
                .findFirst()
                .orElse(NONE);
    }

    public static PurgeType getType(String id) {
        PurgeType type = getTypeByName(id);
        try {
            return type == NONE ? getType(Integer.parseInt(id)) : type;
        } catch (Exception ex) {
            return NONE;
        }
    }
}
