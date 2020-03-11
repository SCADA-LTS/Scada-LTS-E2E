package org.scadalts.e2e.page.core.criterias.identifiers;

public interface IdentifierObject {

    IdentifierObject EMPTY = new IdentifierObject() {
        @Override
        public String getValue() {
            return "";
        }
        @Override
        public String toString() {
            return "EMPTY";
        }
    };

    String getValue();

    static IdentifierObject empty() {
        return EMPTY;
    }
}
