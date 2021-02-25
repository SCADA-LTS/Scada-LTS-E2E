package org.scadalts.e2e.test.core.plans.providers;

import lombok.extern.log4j.Log4j2;
import org.junit.runners.Suite;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.types.TestPlan;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if(config.getClassesTestRefs().length > 0)
            result.addAll(_getTestClassesByRef(config));
        result.addAll(_getTestClasses(config));
        result.add(plans.getPlan(TestPlan.LOGOUT));
        return result.stream()
                .flatMap(this::getStream)
                .collect(Collectors.toList());
    }

    private Stream<? extends Class<?>> getStream(Class<?> test) {
        Set<Annotation> annotations = _getSuite(test);
        if(annotations.isEmpty())
            return Stream.of(test);
        Suite.SuiteClasses suite = (Suite.SuiteClasses)annotations.iterator().next();
        return Stream.of(suite.value())
                .flatMap(this::getStream);
    }

    private Set<Annotation> _getSuite(Class<?> test) {
        return Stream.of(test.getDeclaredAnnotations())
                .filter(a -> a instanceof Suite.SuiteClasses)
                .collect(Collectors.toSet());
    }

    private List<Class<?>> _getTestClasses(E2eConfig config) {
        List<Class<?>> result = new ArrayList<>();
        for(TestPlan testPlan: config.getTestPlans()) {
            if (plans.containsPlan(testPlan))
                result.add(plans.getPlan(testPlan));
        }
        return result;
    }

    private List<Class<?>> _getTestClassesByRef(E2eConfig config) {
        String[] value = config.getClassesTestRefs();
        return parse(value);
    }

}
