package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class DeleteGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();
    private GraphicalViewTestsUtil testsUtil;
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void createView() {
        testsUtil = new GraphicalViewTestsUtil(E2eAbstractRunnable.getNavigationPage(), viewName);
        testsUtil.addView();
        graphicalViewsPageSubject = testsUtil.getGraphicalViewsPage();
    }

    @After
    public void clean() {
        testsUtil.clean();
    }

    @Test
    public void test_delete_view() {

        //when:
        graphicalViewsPageSubject
                .openViewEditor(viewName)
                .delete();

        //then:
        String body = graphicalViewsPageSubject.reopen().getBodyText();

        assertThat(body, not(containsString(viewName)));
    }
}