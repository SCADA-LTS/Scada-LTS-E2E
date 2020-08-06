package org.scadalts.e2e.test.impl.tests.performance.storungs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesNumberLoggingAllDataPerformanceTest;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesNumberOnChangePerformanceTest;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesNumberTwoPointsPerformanceTest;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesNumberZeroToOnesPerformanceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        GetLivesNumberOnChangePerformanceTest.class,
        GetLivesNumberLoggingAllDataPerformanceTest.class,
        GetLivesNumberZeroToOnesPerformanceTest.class,
        GetLivesNumberTwoPointsPerformanceTest.class
})
public class StorungsAndAlarmsPerformanceTestsSuite {
}
