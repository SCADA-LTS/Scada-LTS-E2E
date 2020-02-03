package org.scadalts.e2e.test.core.plan.exec;

import lombok.Data;
import org.junit.runner.Result;

@Data
public class TestResult {

    private final String testName;
    private final Result result;

}
