package org.scadalts.e2e.test.core.plans.engine;

import java.time.LocalDateTime;
import java.util.*;

public interface E2eSummarable {
    int getRunCount();

    int getFailureCount();

    long getRunTime();

    int getIgnoreCount();

    boolean wasSuccessful();

    List<E2eFailure> getFailures();

    List<String> getFailTestNames();

    Map<String, TestStatus> getTestStatuses();

    //Map<String, String> getTestDescriptions();

    String getUrl();

    Map<String, String> getStatusesLegend();

    Map<String, LocalDateTime> getStartTimes();

    Map<String, LocalDateTime> getEndTimes();

    static E2eSummarable empty() {
        return new E2eSummary(Collections.emptyMap());
    }

    static E2eSummarable summary(Map<Class<?>, List<E2eResult>> results) {
        return new E2eSummary(new HashMap<>(results));
    }
}
