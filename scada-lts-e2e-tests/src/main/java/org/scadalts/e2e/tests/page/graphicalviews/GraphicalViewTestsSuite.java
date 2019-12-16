package org.scadalts.e2e.tests.page.graphicalviews;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateGraphicalViewTest.class,
        MultiTabGraphicalViewTest.class,
        DeleteGraphicalViewTest.class
})
public class GraphicalViewTestsSuite {
}
