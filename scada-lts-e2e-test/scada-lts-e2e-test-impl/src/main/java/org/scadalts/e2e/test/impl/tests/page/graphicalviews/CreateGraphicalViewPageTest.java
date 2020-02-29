package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestObjectsUtil;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(E2eTestRunner.class)
public class CreateGraphicalViewPageTest {

    private final GraphicalViewIdentifier viewName = IdentifierObjectFactory.viewName();
    private GraphicalViewTestObjectsUtil graphicalViewTestsUtil;
    private GraphicalViewsPage graphicalViewsPageSubject;
    private File background;

    @Before
    public void setup() {
        GraphicalViewCriteria criteria = new GraphicalViewCriteria(viewName);
        graphicalViewTestsUtil = new GraphicalViewTestObjectsUtil(E2eAbstractRunnable.getNavigationPage(), criteria);
        background = graphicalViewTestsUtil.getBackgroundFile();
        graphicalViewsPageSubject = graphicalViewTestsUtil.openPage();
    }

    @After
    public void clean() {
        graphicalViewTestsUtil.deleteObjects();
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

        assertThat(body, containsString(viewName.getValue()));
    }
}
