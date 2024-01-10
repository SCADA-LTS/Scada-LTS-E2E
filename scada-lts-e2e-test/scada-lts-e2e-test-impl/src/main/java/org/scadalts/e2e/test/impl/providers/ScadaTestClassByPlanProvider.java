package org.scadalts.e2e.test.impl.providers;

import org.scadalts.e2e.common.core.types.TestPlan;
import org.scadalts.e2e.test.core.plans.providers.TestClassByPlanProvider;
import org.scadalts.e2e.test.impl.backstop.GenerateBackstopConfigJson;
import org.scadalts.e2e.test.impl.config.auto.AlarmNotificationAutomaticConfiguration;
import org.scadalts.e2e.test.impl.config.auto.ConfigForProdChecker;
import org.scadalts.e2e.test.impl.config.auto.PerformAutomaticConfiguration;
import org.scadalts.e2e.test.impl.config.auto.tasks.exports.Exporter;
import org.scadalts.e2e.test.impl.groovy.GroovyEngine;
import org.scadalts.e2e.test.impl.tests.ScadaAllTestsSuite;
import org.scadalts.e2e.test.impl.tests.ScadaPageAndServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.ScadaProdTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.ScadaCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.login.LoginCheckTest;
import org.scadalts.e2e.test.impl.tests.check.login.LogoutCheckTest;
import org.scadalts.e2e.test.impl.tests.page.ScadaPageTestsSuite;
import org.scadalts.e2e.test.impl.tests.performance.ScadaPerformanceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.ScadaServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.eventdetector.EventDetectorServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.eventhandler.EventHandlerServiceTestsSuite;
import org.scadalts.e2e.test.impl.tests.service.storungs.StorungsAndAlarmsServiceTestsSuite;

import java.util.HashMap;
import java.util.Map;

public class ScadaTestClassByPlanProvider implements TestClassByPlanProvider {

    private static final Map<TestPlan, Class<?>> tests = new HashMap<>();

    static {
        tests.put(TestPlan.CHECK, ScadaCheckTestsSuite.class);
        tests.put(TestPlan.PAGE, ScadaPageTestsSuite.class);
        tests.put(TestPlan.ALL, ScadaAllTestsSuite.class);
        tests.put(TestPlan.SERVICE, ScadaServiceTestsSuite.class);
        tests.put(TestPlan.PAGE_SERVICE, ScadaPageAndServiceTestsSuite.class);
        tests.put(TestPlan.SERVICE_PAGE, ScadaPageAndServiceTestsSuite.class);
        tests.put(TestPlan.LOGIN, LoginCheckTest.class);
        tests.put(TestPlan.LOGOUT, LogoutCheckTest.class);
        tests.put(TestPlan.AUTO_CONFIG, PerformAutomaticConfiguration.class);
        tests.put(TestPlan.CONFIG_AUTO, PerformAutomaticConfiguration.class);
        tests.put(TestPlan.EXPORT, Exporter.class);
        //tests.put(TestPlan.IMPORT, Importer.class);
        //tests.put(TestPlan.CLEAN, Cleaner.class);
        tests.put(TestPlan.PERFORMANCE, ScadaPerformanceTestsSuite.class);
        tests.put(TestPlan.PERF, ScadaPerformanceTestsSuite.class);
        tests.put(TestPlan.ALARM_NOTIFICATION_AUTO_CONFIG, AlarmNotificationAutomaticConfiguration.class);
        tests.put(TestPlan.PROD, ScadaProdTestsSuite.class);
        tests.put(TestPlan.CONFIG_PROD_CHECKER, ConfigForProdChecker.class);
        tests.put(TestPlan.FROM_GROOVY_SCRIPT, GroovyEngine.class);
        tests.put(TestPlan.GROOVY_SCRIPT, GroovyEngine.class);
        tests.put(TestPlan.ALARM_STORUNG, StorungsAndAlarmsServiceTestsSuite.class);
        tests.put(TestPlan.EVENT_DETECTOR_SERVICE, EventDetectorServiceTestsSuite.class);
        tests.put(TestPlan.EVENT_HANDLER_SERVICE, EventHandlerServiceTestsSuite.class);
        tests.put(TestPlan.GEN_BACKSTOP_CONFIG, GenerateBackstopConfigJson.class);
    }

    @Override
    public boolean containsPlan(TestPlan plan) {
        return tests.containsKey(plan);
    }

    @Override
    public Class<?> getTestClass(TestPlan testPlan) {
        return tests.computeIfAbsent(testPlan, (plan) -> {throw new IllegalArgumentException(plan.name());});
    }
}
