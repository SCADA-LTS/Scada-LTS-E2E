package org.scadalts.e2e.test.core.plan.runner;

import org.junit.runner.JUnitCore;
import org.scadalts.e2e.test.core.plan.exec.TestResult;

import java.util.List;

public interface TestsRunnable {
    List<TestResult> runs(List<Class<?>> tests);

    static TestsRunnable newInstance() {
        return new E2eTestsRunner(new JUnitCore());
    }
}
