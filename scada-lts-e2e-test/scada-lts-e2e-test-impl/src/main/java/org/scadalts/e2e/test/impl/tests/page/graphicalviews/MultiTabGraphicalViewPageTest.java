package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(E2eTestRunner.class)
public class MultiTabGraphicalViewPageTest {

    private final GraphicalViewIdentifier viewName = IdentifierObjectFactory.viewName();
    private GraphicalViewObjectsCreator graphicalViewTestsUtil;
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void createView() {
        logger.info("viewName: {}", viewName.getValue());
        GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(viewName);
        graphicalViewTestsUtil = new GraphicalViewObjectsCreator(E2eAbstractRunnable.getNavigationPage(), criteria);
        graphicalViewsPageSubject = graphicalViewTestsUtil.createObjects();
    }

    @After
    public void clean() {
        NavigationPage.closeAllButOnePage();
        graphicalViewTestsUtil.deleteObjects();
    }

    @Test
    public void test_open_multi_tab_for_view() {

        //when:
        graphicalViewsPageSubject.openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure()
                .openInNewTab()
                .selectViewByName(viewName)
                .printLoadingMeasure();

        //then:
        Set<String> tabs = NavigationPage.tabsOpened();
        assertEquals(6, tabs.size());
    }
}
