package org.scadalts.e2e.test.impl.tests.service.alarms;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetAlarmsLivePaginationServiceTest.class,
        GetActiveAlarmStorungLiveServiceTest.class,
        GetInactiveAlarmStorungLiveServiceTest.class,
        AcknowledgeAlarmStorungLiveServiceTest.class,
        SequenceAlarmStorungLiveServiceTest.class,
        StartActiveAlarmStorungLiveServiceTest.class,
        StartInactiveAlarmStorungLiveServiceTest.class
})
public class StorungsAndAlarmsServiceTestsSuite {
}
