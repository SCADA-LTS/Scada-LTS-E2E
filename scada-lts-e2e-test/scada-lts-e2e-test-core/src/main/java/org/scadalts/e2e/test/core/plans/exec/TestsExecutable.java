package org.scadalts.e2e.test.core.plans.exec;

import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.scadalts.e2e.test.core.plans.engine.TestsRunEngine;
import org.scadalts.e2e.test.core.plans.providers.TestClassesProvider;

@FunctionalInterface
public interface TestsExecutable {

    E2eSummarable execute(E2eConfig config);

    static TestsExecutable newExecutor(TestClassesProvider testClassesProvider, TestsRunEngine testsCore) {
        return new TestsExecutor(testClassesProvider, testsCore);
    }
}
