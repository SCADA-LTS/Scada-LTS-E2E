package org.scadalts.e2e.test.impl.tests.page.graphicalviews.run;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.CreateGraphicalViewTest;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.DeleteGraphicalViewTest;
import org.scadalts.e2e.test.impl.tests.page.graphicalviews.MultiTabGraphicalViewTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateGraphicalViewTest.class,
        MultiTabGraphicalViewTest.class,
        DeleteGraphicalViewTest.class
})
public class GraphicalViewTestsRunnableSuite extends E2eAbstractRunnable {
}
