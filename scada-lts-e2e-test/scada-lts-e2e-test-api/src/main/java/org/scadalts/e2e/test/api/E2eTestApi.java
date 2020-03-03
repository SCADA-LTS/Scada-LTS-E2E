package org.scadalts.e2e.test.api;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.plans.exec.TestsExecutable;
import org.scadalts.e2e.test.core.plans.providers.TestClassesProvider;
import org.scadalts.e2e.test.core.plans.runner.E2eResultSummary;
import org.scadalts.e2e.test.core.plans.runner.TestsRunnable;
import org.scadalts.e2e.test.impl.providers.ScadaTestClassByPlanProvider;

public interface E2eTestApi {

    E2eResultSummary run(E2eConfig config);

    static E2eTestApi newInstance() {
        TestClassesProvider provider = TestClassesProvider.newInstance(new ScadaTestClassByPlanProvider());
        TestsExecutable executable = TestsExecutable.newExecutor(provider, TestsRunnable.newInstance());
        return new E2eTestApiImpl(executable);
    }
}
