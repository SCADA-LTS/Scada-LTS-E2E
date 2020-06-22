package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

@Getter
public enum DataPointNotifierType implements DictionaryObject {
    
    ALARM("AL", "alarm"),
    STORUNG("ST", "storung");
    
    private final String id;
    private final String name;

    DataPointNotifierType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
