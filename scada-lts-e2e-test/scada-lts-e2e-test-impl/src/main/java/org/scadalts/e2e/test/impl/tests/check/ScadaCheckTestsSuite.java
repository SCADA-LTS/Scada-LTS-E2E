package org.scadalts.e2e.test.impl.tests.check;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.GraphicalViewTestsSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventDetectorCheckTest.class,
        GraphicalViewTestsSuite.class
})
public class ScadaCheckTestsSuite {
}
