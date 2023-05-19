package org.scadalts.e2e.test.core.plans.engine;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDateTime;
@Log4j2
@Getter
public class E2eSummaryUnit {

    private final String testName;
    private final String executeTimeFormatted;
    private final String runTimeFormatted;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final TestStatus status;

    public E2eSummaryUnit(String testName, String executeTimeFormatted, String runTimeFormatted,
                          LocalDateTime startDateTime, LocalDateTime endDateTime,
                          TestStatus status) {
        this.testName = testName;
        this.executeTimeFormatted = executeTimeFormatted;
        this.runTimeFormatted = runTimeFormatted;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }
}
