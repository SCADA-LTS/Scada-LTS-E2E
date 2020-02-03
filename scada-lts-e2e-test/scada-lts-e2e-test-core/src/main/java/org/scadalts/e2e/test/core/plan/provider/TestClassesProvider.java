package org.scadalts.e2e.test.core.plan.provider;

import org.scadalts.e2e.common.config.E2eConfig;

import java.util.List;

public interface TestClassesProvider {
    List<Class<?>> getClasses(E2eConfig config);

    static TestClassesProvider newInstance(TestClassByPlanProvider testClassByPlanProvider) {
        return new TestClassesProviderImpl(testClassByPlanProvider);
    }
}
