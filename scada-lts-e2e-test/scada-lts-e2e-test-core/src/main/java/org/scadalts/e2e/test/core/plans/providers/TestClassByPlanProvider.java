package org.scadalts.e2e.test.core.plans.providers;

import org.scadalts.e2e.common.core.types.TestPlan;

public interface TestClassByPlanProvider {
    boolean containsPlan(TestPlan plan);
    Class<?> getTestClass(TestPlan plan);
}
