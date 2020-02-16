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

    private final String typeName;
    private final String id;

    ChangeTypeField(String typeName, String id) {
        this.typeName = typeName;
        this.id = id;
    }

}
