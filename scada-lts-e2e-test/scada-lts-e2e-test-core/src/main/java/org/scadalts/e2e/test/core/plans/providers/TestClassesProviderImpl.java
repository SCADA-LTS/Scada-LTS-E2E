package org.scadalts.e2e.test.core.plans.providers;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.types.TestPlan;

import java.util.ArrayList;
import java.util.List;

import static org.scadalts.e2e.test.core.plans.providers.TestsParser.parse;

@Log4j2
class TestClassesProviderImpl implements TestClassesProvider {

    private final TestClassByPlanProvider plans;

    TestClassesProviderImpl(TestClassByPlanProvider plans) {
        this.plans = plans;
    }

    @Override
    public List<Class<?>> getClasses(E2eConfig config) {
        return _getTestClassesFromConsole(config);
    }

    private List<Class<?>> _getTestClassesFromConsole(E2eConfig config) {
        List<Class<?>> result = new ArrayList<>();
        result.add(plans.getPlan(TestPlan.LOGIN));
        if(config.getClassesTestRefs().length > 0)
            result.addAll(_getTestClassesByRef(config));
        result.addAll(_getTestClasses(config));
        result.add(plans.getPlan(TestPlan.LOGOUT));
        return result;
    }

    private List<Class<?>> _getTestClasses(E2eConfig config) {
        List<Class<?>> result = new ArrayList<>();
        if(plans.containsPlan(config.getTestPlan()))
            result.add(plans.getPlan(config.getTestPlan()));
        return result;
    }

    private List<Class<?>> _getTestClassesByRef(E2eConfig config) {
        String[] value = config.getClassesTestRefs();
        return parse(value);
    }

}
