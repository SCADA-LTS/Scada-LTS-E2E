package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangeAlarmListCheckTest.class,
        ClickAllGraphicalViewCheckTest.class,
        EditGraphicalViewAndOpenMainPageCheckTest.class
})
public class GraphicalViewsCheckTestsSuite {
}
