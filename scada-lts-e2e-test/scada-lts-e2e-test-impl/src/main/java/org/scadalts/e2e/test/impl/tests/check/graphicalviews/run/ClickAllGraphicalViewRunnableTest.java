package org.scadalts.e2e.test.impl.tests.check.graphicalviews.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.ClickAllGraphicalViewCheckTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClickAllGraphicalViewCheckTest.class
})
public class ClickAllGraphicalViewRunnableTest extends E2eAbstractRunnable {
}
