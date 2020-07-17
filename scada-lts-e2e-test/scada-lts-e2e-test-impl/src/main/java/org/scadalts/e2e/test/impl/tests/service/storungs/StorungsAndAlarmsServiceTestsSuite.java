package org.scadalts.e2e.test.impl.tests.service.storungs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.service.storungs.live.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetLivesPaginationServiceTest.class,
        GetActiveLivesServiceTest.class,
        GetInactiveLivesServiceTest.class,
        AcknowledgeLivesServiceTest.class,
        GetLivesAggregationServiceTest.class,
        GetStartActiveLivesServiceTest.class,
        GetStartInactiveLivesServiceTest.class,
        GetLivesGroupingSortServiceTest.class,
        GetAllLivesParametersServiceTest.class,
        GetLivesAggregationPerformanceServiceTest.class,
        GetLivesAggregationAlarmFiveSizeSeqServiceTest.class
})
public class StorungsAndAlarmsServiceTestsSuite {
}
