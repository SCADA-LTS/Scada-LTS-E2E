package org.scadalts.e2e.test.impl.providers;

import org.scadalts.e2e.common.types.TestPlan;
import org.scadalts.e2e.test.core.plan.provider.TestClassByPlanProvider;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.ScadaAllTestsRunnableSuite;
import org.scadalts.e2e.test.impl.tests.check.ScadaCheckTestsRunnableSuite;
import org.scadalts.e2e.test.impl.tests.page.ScadaPageTestsRunnableSuite;
import org.scadalts.e2e.test.impl.tests.webservice.ScadaWebServiceTestsRunnableSuite;

import java.util.HashMap;
import java.util.Map;

public class ScadaTestClassByPlanProvider implements TestClassByPlanProvider {

    private static Map<TestPlan, Class<? extends E2eAbstractRunnable>> tests = new HashMap<>();

    static {
        tests.put(TestPlan.CHECK, ScadaCheckTestsRunnableSuite.class);
        tests.put(TestPlan.PAGE, ScadaPageTestsRunnableSuite.class);
        tests.put(TestPlan.ALL, ScadaAllTestsRunnableSuite.class);
        tests.put(TestPlan.WEB_SERVICE, ScadaWebServiceTestsRunnableSuite.class);
    }

    @Override
    public boolean containsPlan(TestPlan plan) {
        return tests.containsKey(plan);
    }

    public Class<? extends E2eAbstractRunnable> getPlan(TestPlan testPlan) {
        return tests.computeIfAbsent(testPlan, (plan) -> {throw new IllegalArgumentException(plan.name());});
    }
}
