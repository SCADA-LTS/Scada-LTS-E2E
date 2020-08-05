package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum DataPointNotifierType implements DictionaryObject {
    
    ALARM("AL", "alarms", AlarmLevel.URGENT),
    STORUNG("ST", "storungs", AlarmLevel.INFORMATION),
    NONE("NONE", "none", AlarmLevel.ANY);
    
    private final String id;
    private final String name;
    private final DictionaryObject alarmLevel;

    DataPointNotifierType(String id, String name, DictionaryObject alarmLevel) {
        this.id = id;
        this.name = name;
        this.alarmLevel = alarmLevel;
    }

    public static DataPointNotifierType getTypeByLevel(AlarmLevel alarmLevel) {
        return Stream.of(DataPointNotifierType.values())
                .filter(a -> a.alarmLevel == alarmLevel)
                .findFirst()
                .orElse(DataPointNotifierType.NONE);
    }

    public static DataPointNotifierType getTypeByLevel(String alarmLevel) {
        return getTypeByLevel(AlarmLevel.getType(alarmLevel));
    }
}
