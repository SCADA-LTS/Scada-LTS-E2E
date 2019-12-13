package org.scadalts.e2e.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.tests.E2e;
import org.scadalts.e2e.pages.page.graphicalviews.EditViewPage;
import org.scadalts.e2e.pages.page.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.pages.page.main.MainPage;

import java.io.File;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MultiTabGraphicalViewTest extends E2e {

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

    @After
    public void clean() {
        String viewId = subjectPage.selectViewByName(viewName);
        EditViewPage editor = subjectPage.openViewEditor(viewId);
        editor.delete();
    }

    @Test
    public void test_multi_tab_for_view() {
        //when:
        subjectPage.openInNewTab();
        subjectPage.selectViewByName(viewName);
        subjectPage.openInNewTab();
        subjectPage.selectViewByName(viewName);
        subjectPage.openInNewTab();
        subjectPage.selectViewByName(viewName);
        subjectPage.openInNewTab();
        subjectPage.selectViewByName(viewName);
        subjectPage.openInNewTab();
        subjectPage.selectViewByName(viewName);

        //then:
        Set<String> tabs = MainPage.tabsOpened();
        assertEquals(6, tabs.size());
    }
}
