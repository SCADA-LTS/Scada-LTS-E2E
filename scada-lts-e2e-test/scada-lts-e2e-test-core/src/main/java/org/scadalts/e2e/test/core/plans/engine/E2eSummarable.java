package org.scadalts.e2e.test.core.plans.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface E2eSummarable {
    int getRunCount();

    int getFailureCount();

    long getRunTime();

    int getIgnoreCount();

    boolean wasSuccessful();

    List<E2eFailure> getFailures();

    Set<String> getFailTestNames();

    String getUrl();

    static E2eSummarable empty() {
        return new E2eSummary(Collections.emptyList());
    }

    static E2eSummarable summary(List<E2eResult> results) {
        return new E2eSummary(new ArrayList<>(results));
    }
}
