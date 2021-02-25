package org.scadalts.e2e.test.core.plans.engine;

import lombok.Getter;

@Getter
public enum TestStatus {

    OK("e2e.test.status.ok"),
    NON_DETERMINISTIC_ERROR("e2e.test.status.non-deterministic-error"),
    ERROR("e2e.test.status.error"),
    NONE("e2e.test.status.none");

    private String description;

    TestStatus(String description) {
        this.description = description;
    }
}
