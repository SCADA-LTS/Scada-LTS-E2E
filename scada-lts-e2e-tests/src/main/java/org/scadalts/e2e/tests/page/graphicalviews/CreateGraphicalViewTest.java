package org.scadalts.e2e.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Test;
import org.scadalts.e2e.tests.E2e;
import org.scadalts.e2e.pages.page.graphicalviews.EditViewPage;
import org.scadalts.e2e.pages.page.graphicalviews.GraphicalViewsPage;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class CreateGraphicalViewTest extends E2e {

    private GraphicalViewsPage subjectPage = getHomePage().openGraphicalViews();
    private static final String viewName = "viewNameTest" + System.currentTimeMillis();

    @After
    public void clean() {
        String viewId = subjectPage.selectViewByName(viewName);
        EditViewPage editor = subjectPage.openViewEditor(viewId);
        editor.delete();
    }

    @Test
    public void test_save_view() {
        //given:
        EditViewPage creator = subjectPage.openViewCreator();
        File backgroundFile = getBackgroundFile();

        creator.chooseFile(backgroundFile);
        creator.uploadFile();
        creator.setViewName(viewName);
        creator.selectComponentByName("Alarms List");
        creator.addViewComponent();
        creator.dragAndDropViewComponent();

        //when:
        creator.save();

        //then:
        String body = subjectPage.getBodyText();
        assertThat(body, containsString(viewName));
    }
}
