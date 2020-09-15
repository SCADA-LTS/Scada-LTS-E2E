package org.scadalts.e2e.test.impl.tests.performance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.performance.storungs.StorungsAndAlarmsPerformanceTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        StorungsAndAlarmsPerformanceTestsSuite.class
})
public class ScadaPerformanceTestsSuite {
}
