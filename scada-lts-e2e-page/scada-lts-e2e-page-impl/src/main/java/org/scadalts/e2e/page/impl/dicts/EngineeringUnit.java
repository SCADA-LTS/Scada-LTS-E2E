package org.scadalts.e2e.page.impl.dicts;

import org.scadalts.e2e.common.core.dicts.DictionaryObject;

public interface EngineeringUnit extends DictionaryObject {
    int getUnitValue();
    String getUnitName();
    String getUnitSuffix();
    String getUnitKey();

    @Override
    default String getId() {
        return String.valueOf(getUnitValue());
    }

    @Override
    default String getName() {
        return getUnitName() + " " + getUnitSuffix();
    }
}
