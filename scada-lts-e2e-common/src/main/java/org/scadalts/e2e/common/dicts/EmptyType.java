package org.scadalts.e2e.common.dicts;

public enum EmptyType implements DictionaryObject {
    ANY;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getId() {
        return "";
    }
}
