package org.scadalts.e2e.test.core.plan.exec;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.plan.provider.TestClassesProvider;
import org.scadalts.e2e.test.core.plan.runner.TestsRunnable;

public interface TestsExecutable {
    boolean execute(E2eConfig config);

    static TestsExecutable newExecutor(TestClassesProvider testClassesProvider, TestsRunnable testsRunnable) {
        return new TestsExecutor(testClassesProvider, testsRunnable);
    }
}
