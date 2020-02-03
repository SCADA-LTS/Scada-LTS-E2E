package org.scadalts.e2e.test.impl.tests.check.eventdetectors.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventDetectorCheckTest.class
})
public class EventDetectorCheckRunnableTest extends E2eAbstractRunnable {
}
