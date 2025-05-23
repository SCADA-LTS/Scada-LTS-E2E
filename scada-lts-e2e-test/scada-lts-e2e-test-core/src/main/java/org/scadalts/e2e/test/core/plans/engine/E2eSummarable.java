package org.scadalts.e2e.test.core.plans.engine;

import java.util.*;

public interface E2eSummarable {
    int getRunCount();

    int getFailureCount();

    long getRunTime();

    long getExecuteTime();

    int getIgnoreCount();

    boolean wasSuccessful();

    List<E2eFailure> getFailures();
    List<E2eSummaryUnit> getResults();

    List<String> getFailTestNames();

    Map<String, TestStatus> getTestStatuses();

    //Map<String, String> getTestDescriptions();*/

    String getUrl();

    Map<String, String> getStatusesLegend();
/*
    Map<String, LocalDateTime> getStartTimes();

    Map<String, LocalDateTime> getEndTimes();

    Map<String, Double> getTestExecuteTime();

    Map<String, Double> getTestRuntime();

    Map<String, String> getTimeFormatted(Map<String, Double> map);*/

    static E2eSummarable empty() {
        return new E2eSummary(Collections.emptyMap());
    }

    static E2eSummarable summary(Map<Class<?>, List<E2eResult>> results) {
        return new E2eSummary(new HashMap<>(results));
    }
}
