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
        SequentialChangesPointValueLivesServiceTest.class,
        StartActiveLivesServiceTest.class,
        StartInactiveLivesServiceTest.class,
        GetLivesStructureServiceTest.class
})
public class StorungsAndAlarmsServiceTestsSuite {
}
