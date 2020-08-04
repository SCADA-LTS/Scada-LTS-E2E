package org.scadalts.e2e.test.impl.tests.service.storungs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.service.storungs.live.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetLivesPaginationAllDataLoggingServiceTest.class,
        GetActiveLivesAllDataLoggingServiceTest.class,
        GetInactiveLivesAllDataLoggingServiceTest.class,
        AcknowledgeLivesServiceTest.class,
        GetLivesAggregationServiceTest.class,
        GetStartActiveLivesAllDataLoggingServiceTest.class,
        GetStartInactiveLivesAllDataLoggingServiceTest.class,
        GetLivesGroupingSortAllDataLoggingServiceTest.class,
        GetAllLivesParametersServiceTest.class,
        GetLivesAggregationAlarmFiveSizeSeqServiceTest.class,
        GetActiveLivesOnChangeServiceTest.class,
        GetInactiveLivesOnChangeServiceTest.class,
        GetLivesGroupingSortOnChangeServiceTest.class,
        GetLivesPaginationOnChangeServiceTest.class,
        GetStartActiveLivesOnChangeServiceTest.class,
        GetStartInactiveLivesOnChangeServiceTest.class
})
public class StorungsAndAlarmsServiceTestsSuite {
}
