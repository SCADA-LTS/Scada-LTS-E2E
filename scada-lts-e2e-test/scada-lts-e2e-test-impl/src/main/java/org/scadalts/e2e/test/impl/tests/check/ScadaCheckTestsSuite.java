package org.scadalts.e2e.test.impl.tests.check;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.GraphicalViewsCheckTestsSuite;
import org.scadalts.e2e.test.impl.tests.check.pointlinks.ChangePointValueViaPointLinksCheckTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventDetectorCheckTest.class,
        GraphicalViewsCheckTestsSuite.class,
        DataPointDetailsCheckTestsSuite.class,
        ChangePointValueViaPointLinksCheckTest.class
})
public class ScadaCheckTestsSuite {
}
