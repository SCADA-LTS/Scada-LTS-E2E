package org.scadalts.e2e.test.core.plan.runner;

import java.util.List;
import java.util.Set;

public interface E2eResultSummarable {
    int getRunCount();

    int getFailureCount();

    long getRunTime();

    int getIgnoreCount();

    boolean wasSuccessful();

    List<E2eFailure> getFailures();

    Set<String> getFailTestNames();
}
