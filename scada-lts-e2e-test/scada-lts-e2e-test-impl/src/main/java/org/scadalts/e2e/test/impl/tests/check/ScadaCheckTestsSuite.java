package org.scadalts.e2e.test.impl.tests.check;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.ClickAllGraphicalViewCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventDetectorCheckTest.class,
        ClickAllGraphicalViewCheckTest.class
})
public class ScadaCheckTestsSuite {
}
