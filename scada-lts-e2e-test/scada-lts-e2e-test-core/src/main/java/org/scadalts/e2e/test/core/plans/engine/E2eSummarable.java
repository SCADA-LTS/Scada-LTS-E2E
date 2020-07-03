package org.scadalts.e2e.test.core.plans.engine;

import java.util.*;

public interface E2eSummarable {
    int getRunCount();

    int getFailureCount();

    long getRunTime();

    int getIgnoreCount();

    boolean wasSuccessful();

    List<E2eFailure> getFailures();

    Set<String> getFailTestNames();

    Map<String, TestStatus> getTestStatuses();

    String getUrl();

    Map<String, String> getStatusesLegend();

    static E2eSummarable empty() {
        return new E2eSummary(Collections.emptyMap());
    }

    static E2eSummarable summary(Map<Class<?>, List<E2eResult>> results) {
        return new E2eSummary(new HashMap<>(results));
    }
}
