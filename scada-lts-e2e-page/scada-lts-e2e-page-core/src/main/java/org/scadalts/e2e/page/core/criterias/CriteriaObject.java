package org.scadalts.e2e.page.core.criterias;

import org.scadalts.e2e.common.dicts.E2eDictionary;

public interface CriteriaObject {

    IdentifierObject getIdentifier();
    E2eDictionary getType();
    String getXpath();
}
