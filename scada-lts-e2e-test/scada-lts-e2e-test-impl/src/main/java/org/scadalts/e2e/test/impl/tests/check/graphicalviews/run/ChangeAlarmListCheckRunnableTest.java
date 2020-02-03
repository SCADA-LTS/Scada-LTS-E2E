package org.scadalts.e2e.test.impl.tests.check.graphicalviews.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.ChangeAlarmListCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangeAlarmListCheckTest.class
})
public class ChangeAlarmListCheckRunnableTest extends E2eAbstractRunnable {
}
