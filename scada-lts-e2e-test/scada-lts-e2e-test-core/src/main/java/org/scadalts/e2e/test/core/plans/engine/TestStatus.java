package org.scadalts.e2e.test.core.plans.engine;

import lombok.Getter;

@Getter
public enum TestStatus {

    OK("The test was successful."),
    NON_DETERMINISTIC_ERROR("If the test fails, it repeats, if it passes the second time, it means that the problem is non-deterministic."),
    ERROR("Two attempts to perform the test failed."),
    NONE("No status means an error with the testing system, because this is not normal, each test has status.");

    private String description;

    TestStatus(String description) {
        this.description = description;
    }
}
