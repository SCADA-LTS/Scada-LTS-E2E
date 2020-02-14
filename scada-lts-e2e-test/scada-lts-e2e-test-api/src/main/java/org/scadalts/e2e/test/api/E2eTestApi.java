package org.scadalts.e2e.test.api;

import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.plan.exec.TestsExecutable;
import org.scadalts.e2e.test.core.plan.provider.TestClassesProvider;
import org.scadalts.e2e.test.core.plan.runner.E2eResultSummary;
import org.scadalts.e2e.test.core.plan.runner.TestsRunnable;
import org.scadalts.e2e.test.impl.providers.ScadaTestClassByPlanProvider;

public interface E2eTestApi {

    E2eResultSummary run(E2eConfig config);

    static E2eTestApi newInstance() {
        TestClassesProvider provider = TestClassesProvider.newInstance(new ScadaTestClassByPlanProvider());
        TestsExecutable executable = TestsExecutable.newExecutor(provider, TestsRunnable.newInstance());
        return new E2eTestApiImpl(executable);
    }
}
