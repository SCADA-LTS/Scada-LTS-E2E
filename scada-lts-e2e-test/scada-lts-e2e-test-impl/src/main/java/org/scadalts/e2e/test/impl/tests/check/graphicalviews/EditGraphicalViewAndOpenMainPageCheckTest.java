package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(TestWithPageRunner.class)
public class EditGraphicalViewAndOpenMainPageCheckTest {

    private static GraphicalViewsPage graphicalViewsPageSubject;
    private static GraphicalViewIdentifier graphicalViewIdentifier;

    @BeforeClass
    public static void setup() {
        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        graphicalViewsPageSubject = navigationPage.openGraphicalViews();
        graphicalViewIdentifier = new GraphicalViewIdentifier(TestImplConfiguration.graphicalViewName);
    }

    @After
    public void close() {
        NavigationPage.openPage().acceptAlertOnPage();
    }

    @Test
    public void test_when_edit_graphical_view_and_open_other_page_then_this_page() {

        //when:
        graphicalViewsPageSubject.openViewEditor(graphicalViewIdentifier)
                .clickCheckboxDelete();

        //and:
        NavigationPage navigationPage = NavigationPage.openPage().acceptAlertOnPage2();

        //then:
        assertThat(navigationPage.getCurrentUrl(), containsString(NavigationPage.URL_REF));
    }
}
