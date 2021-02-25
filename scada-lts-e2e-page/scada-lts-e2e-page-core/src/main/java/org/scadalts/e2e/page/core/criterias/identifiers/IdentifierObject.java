package org.scadalts.e2e.page.core.criterias.identifiers;

import org.scadalts.e2e.common.core.dicts.DictionaryObject;

import static org.scadalts.e2e.page.core.criterias.Tag.each;


public interface IdentifierObject {

    IdentifierObject EMPTY = new IdentifierObject() {
        @Override
        public String getValue() {
            return "";
        }

        @Override
        public NodeCriteria getNodeCriteria() {
            return NodeCriteria.empty();
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

    String getValue();

    default NodeCriteria getNodeCriteria() {
        return NodeCriteria.exactlyTypeAny(this, each());
    }

    default DictionaryObject getType() {
        return DictionaryObject.ANY;
    }

    static IdentifierObject empty() {
        return EMPTY;
    }
}
