package org.scadalts.e2e.page.core.criterias;

import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

public interface CriteriaObject {

    IdentifierObject getIdentifier();
    default NodeCriteria[] getNodeCriteria() {
        return new NodeCriteria[]{getIdentifier().getNodeCriteria()};
    }

    CriteriaObject EMPTY = new CriteriaObject() {
        @Override
        public IdentifierObject getIdentifier() {
            return IdentifierObject.empty();
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
