package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class CreateGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();
    private GraphicalViewTestsUtil testsUtil;
    private GraphicalViewsPage graphicalViewsPageSubject;
    private File background;

    @Before
    public void setup() {
        testsUtil = new GraphicalViewTestsUtil(E2eAbstractRunnable.getNavigationPage(), viewName);
        background = testsUtil.getBackgroundFile();
        graphicalViewsPageSubject = testsUtil.openGraphicalViews();
    }

    @After
    public void clean() {
        testsUtil.clean();
    }

    @Test
    public void test_save_view() {
        //when:
        graphicalViewsPageSubject.openViewCreator()
                .chooseFile(background)
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
