package org.scadalts.e2e.tests.config;

import org.scadalts.e2e.config.E2eConfigKey;

public enum E2ePlanTestsKeys implements E2eConfigKey {
    CUSTOM_TESTS_PLAN("e2eTests"),
    ALL_TESTS_PLAN("e2eAll"),
    API_TESTS_PLAN("e2eApi"),
    PAGE_TESTS_PLAN("e2ePage"),
    CHECK_TESTS_PLAN("e2eCheck");

    private String key;

    E2ePlanTestsKeys(String key) {
        this.key = key;
    }

    @Override
    public String key() {
        return key;
    }
}
