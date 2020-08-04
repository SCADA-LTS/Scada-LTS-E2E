package org.scadalts.e2e.test.impl.tests.performance.storungs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesAggregationLoggingAllDataPerformanceTest;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesAggregationOnChangePerformanceTest;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesAggregationTwoPointsPerformanceTest;
import org.scadalts.e2e.test.impl.tests.performance.storungs.live.GetLivesAggregationZeroToOnesPerformanceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        GetLivesAggregationOnChangePerformanceTest.class,
        GetLivesAggregationLoggingAllDataPerformanceTest.class,
        GetLivesAggregationZeroToOnesPerformanceTest.class,
        GetLivesAggregationTwoPointsPerformanceTest.class
})
public class StorungsAndAlarmsPerformanceTestsSuite {
}
