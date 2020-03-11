package org.scadalts.e2e.page.core.criterias;

import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

public interface CriteriaObject {

    IdentifierObject getIdentifier();
    DictionaryObject getType();

    CriteriaObject EMPTY = new CriteriaObject() {
        @Override
        public IdentifierObject getIdentifier() {
            return IdentifierObject.empty();
        }

        @Override
        public DictionaryObject getType() {
            return DictionaryObject.any();
        }

        @Override
        public String toString() {
            return "EMPTY";
        }
    };

    static CriteriaObject empty() {
        return EMPTY;
    }
}
