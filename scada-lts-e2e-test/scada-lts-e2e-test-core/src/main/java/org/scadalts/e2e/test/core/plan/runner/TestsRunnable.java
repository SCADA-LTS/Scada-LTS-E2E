package org.scadalts.e2e.test.core.plan.runner;

import org.junit.runner.JUnitCore;

import java.util.List;

public interface TestsRunnable {
    List<E2eResult> run(List<Class<?>> tests);

    static TestsRunnable newInstance() {
        return new E2eTestsRunner(new JUnitCore());
    }
}
