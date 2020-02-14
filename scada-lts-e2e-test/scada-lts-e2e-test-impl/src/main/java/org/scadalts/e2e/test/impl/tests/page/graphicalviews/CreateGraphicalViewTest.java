package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class CreateGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final GraphicalViewTestsUtil testsUtil = new GraphicalViewTestsUtil(navigationPage);
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void setup() throws ConfigureTestException {
        graphicalViewsPageSubject = testsUtil.openGraphicalViews();
    }

    @After
    public void clean() throws ConfigureTestException {
        testsUtil.openGraphicalViews().openViewEditor(viewName)
                .delete();
    }

    @Test
    public void test_save_view() {
        //when:
        graphicalViewsPageSubject.openViewCreator()
                .chooseFile(testsUtil.getBackgroundFile())
                .uploadFile()
                .setViewName(viewName)
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .save();

        //then:
        String body = graphicalViewsPageSubject.reopen().getBodyText();

        assertThat(body, containsString(viewName));
    }
}
