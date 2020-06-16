package org.scadalts.e2e.test.impl.tests.service.alarms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetAlarmsLivePaginationServiceTest.class,
        GetActiveAlarmLiveServiceTest.class,
        GetInactiveAlarmLiveServiceTest.class,
        AcknowledgeAlarmLiveServiceTest.class
})
public class StorungsAndAlarmsServiceTestsSuite {
}
