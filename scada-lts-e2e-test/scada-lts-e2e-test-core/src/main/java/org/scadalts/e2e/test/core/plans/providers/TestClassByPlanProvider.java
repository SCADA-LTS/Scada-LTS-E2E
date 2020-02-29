package org.scadalts.e2e.test.core.plans.providers;

import org.scadalts.e2e.common.types.TestPlan;

public interface TestClassByPlanProvider {
    boolean containsPlan(TestPlan plan);
    Class<?> getPlan(TestPlan plan);
}
