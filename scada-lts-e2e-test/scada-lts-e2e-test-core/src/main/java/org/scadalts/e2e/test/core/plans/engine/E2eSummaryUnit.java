package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;
@Log4j2
public class E2eSummaryUnit {

    private final String testName;
    private final String executeTime;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final TestStatus status;

    public E2eSummaryUnit(String testName, String executeTime, LocalDateTime startDateTime, LocalDateTime endDateTime, TestStatus status) {
        this.testName = testName;
        this.executeTime = executeTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public String getTestName() {
        return testName;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public TestStatus getStatus() {
        return status;
    }
}
