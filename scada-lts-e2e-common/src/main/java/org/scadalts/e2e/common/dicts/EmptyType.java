package org.scadalts.e2e.common.dicts;

public enum EmptyType implements E2eDictionary {
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
