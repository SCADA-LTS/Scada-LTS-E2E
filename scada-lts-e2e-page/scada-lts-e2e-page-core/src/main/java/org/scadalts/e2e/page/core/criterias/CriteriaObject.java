package org.scadalts.e2e.page.core.criterias;

import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

public interface CriteriaObject {

    IdentifierObject getIdentifier();
    DictionaryObject getType();
}
