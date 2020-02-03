package org.scadalts.e2e.test.core.plan.provider;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.tests.E2eRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.scadalts.e2e.test.core.plan.provider.TestsParser.parseTestClasses;

@Log4j2
class TestClassesProviderImpl implements TestClassesProvider {

    private final TestClassByPlanProvider plans;

    TestClassesProviderImpl(TestClassByPlanProvider plans) {
        this.plans = plans;
    }

    @Override
    public List<Class<?>> getClasses(E2eConfig config) {
        return _getTestClassesFromConsole(config).stream()
                .filter(this::_isRunnable)
                .collect(Collectors.toList());
    }

    private boolean _isRunnable(Class<?> a) {
        try {
            return a.newInstance() instanceof E2eRunnable;
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
            return false;
        }
    }

    private List<Class<?>> _getTestClassesFromConsole(E2eConfig config) {
        List<Class<?>> result = _getTestsClasses(config);
        if(config.getClassesTestRefs().length > 0)
            result.addAll(_getTestClassesByRef(config));
        return result;
    }

    private List<Class<?>> _getTestsClasses(E2eConfig config) {
        List<Class<?>> result = new ArrayList<>();
        if(plans.containsPlan(config.getTestPlan()))
            result.add(plans.getPlan(config.getTestPlan()));
        return result;
    }

    private List<Class<?>> _getTestClassesByRef(E2eConfig config) {
        String[] value = config.getClassesTestRefs();
        return parseTestClasses(value);
    }

}
