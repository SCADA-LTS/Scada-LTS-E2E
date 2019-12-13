package org.scadalts.e2e.tests.page.graphicalviews;

import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.tests.E2e;
import org.scadalts.e2e.pages.page.graphicalviews.EditViewPage;
import org.scadalts.e2e.pages.page.graphicalviews.GraphicalViewsPage;

import java.io.File;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DeleteGraphicalViewTest extends E2e {

    private GraphicalViewsPage subjectPage = getMainPage().openGraphicalViews();
    private static final String viewName = "viewNameTest" + System.currentTimeMillis();

    @Before
    public void createView() {
        EditViewPage creator = subjectPage.openViewCreator();
        File backgroundFile = getBackgroundFile();

        creator.chooseFile(backgroundFile);
        creator.uploadFile();
        creator.setViewName(viewName);
        creator.selectComponentByName("Alarms List");
        creator.addViewComponent();
        creator.dragAndDropViewComponent();
        creator.save();
    }

    @Test
    public void test_delete_view() {
        //given:
        String viewId = subjectPage.selectViewByName(viewName);
        EditViewPage edit = subjectPage.openViewEditor(viewId);

        //when:
        edit.delete();

        //then:
        String body = subjectPage.getBodyText();
        assertThat(body, not(containsString(viewName)));
    }
}