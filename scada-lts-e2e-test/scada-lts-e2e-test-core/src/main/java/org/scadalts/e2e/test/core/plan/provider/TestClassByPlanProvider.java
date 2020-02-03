package org.scadalts.e2e.test.core.plan.provider;

import org.scadalts.e2e.common.types.TestPlan;
import org.scadalts.e2e.test.core.tests.E2eRunnable;

public interface TestClassByPlanProvider {
    boolean containsPlan(TestPlan plan);
    Class<? extends E2eRunnable> getPlan(TestPlan plan);
}
