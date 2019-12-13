package org.scadalts.e2e.tests.plan;

import org.scadalts.e2e.tests.ScadaAllTestsSuite;
import org.scadalts.e2e.tests.api.ScadaApiTestsSuite;
import org.scadalts.e2e.tests.check.ScadaCheckTestsSuite;
import org.scadalts.e2e.tests.config.E2ePlanTestsKeys;
import org.scadalts.e2e.tests.page.ScadaPageTestsSuite;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.scadalts.e2e.tests.plan.TestsParser.parseTestClasses;

enum PlanTestsProvider {

    INSTANCE;

    Map<E2ePlanTestsKeys, Class<?>> plans = new HashMap<>();

    PlanTestsProvider() {
        plans.put(E2ePlanTestsKeys.ALL_TESTS_PLAN, ScadaAllTestsSuite.class);
        plans.put(E2ePlanTestsKeys.API_TESTS_PLAN, ScadaApiTestsSuite.class);
        plans.put(E2ePlanTestsKeys.PAGE_TESTS_PLAN, ScadaPageTestsSuite.class);
        plans.put(E2ePlanTestsKeys.CHECK_TESTS_PLAN, ScadaCheckTestsSuite.class);
    }

    public List<Class<?>> getPlan(Properties console) {
        if(console.isEmpty())
            return getAllTestsPlan();
        return getConsoleTestsPlan(console);
    }

    private List<Class<?>> getConsoleTestsPlan(Properties console) {
        List<Class<?>> result = getTestsPlan(console);
        if(console.containsKey(E2ePlanTestsKeys.CUSTOM_TESTS_PLAN.key()))
            result.addAll(getCustomTestsPlan(console));
        return result;
    }

    private List<Class<?>> getTestsPlan(Properties ref) {
        return plans.entrySet().stream()
                    .filter(a -> ref.containsKey(a.getKey().key()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
    }

    private List<Class<?>> getCustomTestsPlan(Properties ref) {
        String value = ref.getProperty(E2ePlanTestsKeys.CUSTOM_TESTS_PLAN.key());
        return parseTestClasses(value);
    }

    private List<Class<?>> getAllTestsPlan() {
        Class<?> clazz = plans.get(E2ePlanTestsKeys.ALL_TESTS_PLAN);
        return Stream.of(clazz).collect(Collectors.toList());
    }
}
