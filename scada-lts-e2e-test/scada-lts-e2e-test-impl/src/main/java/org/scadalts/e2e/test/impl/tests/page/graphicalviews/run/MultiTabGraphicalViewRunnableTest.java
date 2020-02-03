package org.scadalts.e2e.test.impl.tests.page.graphicalviews.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.MultiTabGraphicalViewTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        MultiTabGraphicalViewTest.class
})
public class MultiTabGraphicalViewRunnableTest extends E2eAbstractRunnable {
}
