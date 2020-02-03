package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@Log4j2
public class MultiTabGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();

    @Before
    public void createView() {
        logger.info("viewName: {}", viewName);
        GraphicalViewTestsUtil.openGraphicalViews()
                .openViewCreator()
                .chooseFile(GraphicalViewTestsUtil.BACKGROUND_FILE)
                .uploadFile()
                .setViewName(viewName)
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .save();


    }

    @After
    public void clean() {
        GraphicalViewTestsUtil.openGraphicalViews().openViewEditor(viewName)
                .delete();
    }

    @Test
    public void test_multi_tab_for_view() {
        //given:
        GraphicalViewsPage graphicalViewsPage = GraphicalViewTestsUtil.openGraphicalViews();

        //when:
        graphicalViewsPage.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        graphicalViewsPage.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        graphicalViewsPage.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        graphicalViewsPage.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        graphicalViewsPage.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        //then:
        Set<String> tabs = NavigationPage.tabsOpened();
        assertEquals(6, tabs.size());
    }
}
