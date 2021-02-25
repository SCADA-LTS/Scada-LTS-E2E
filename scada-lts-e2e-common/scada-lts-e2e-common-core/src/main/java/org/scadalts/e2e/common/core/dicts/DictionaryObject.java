package org.scadalts.e2e.common.core.dicts;

public interface DictionaryObject {

    String getName();
    default String getId() {
        return "";
    }

    DictionaryObject ANY = new DictionaryObject() {
        @Override
        public String getName() {
            return "";
        }

        @Override
        public String getId() {
            return "";
        }

        @Override
        public String toString() {
            return "ANY";
        }
    };

    static DictionaryObject any() {
        return ANY;
    }

}
