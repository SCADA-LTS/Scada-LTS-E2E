package org.scadalts.e2e.page.impl.dict;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

@Getter
public enum ChangeTypeField implements E2eDictionary {

    START_VALUE("Start value", "startValue"),
    ROLL("Roll", "roll"),
    MIN("Minimum", "min"),
    MAX("Maximum", "max"),
    MAX_CHANGE("Maximum change", "maxChange"),
    CHANGE("Change", "change"),
    VOLATILITY("Volatility", "volatility"),
    ATTRACTION_POINT("Attraction point", "attractionPointId");

    private final String name;
    private final String id;

    ChangeTypeField(String name, String id) {
        this.name = name;
        this.id = id;
    }

}
