package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import java.text.MessageFormat;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(E2eTestRunner.class)
public class ClickAllGraphicalViewCheckTest {

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final GraphicalViewTestsUtil testsUtil = new GraphicalViewTestsUtil(navigationPage);

    @Test
    public void test_click_all_graphical_view() throws ConfigureTestException {
        //given:
        GraphicalViewsPage graphicalViewsPage = testsUtil.openGraphicalViews();
        Map<String, String> dataViews = graphicalViewsPage.getDataAllViews();

        for (Map.Entry<String, String> view : dataViews.entrySet()) {
            //when:
            boolean selected = graphicalViewsPage
                    .selectViewByName(view.getValue())
                    .waitOnLoadedBackground()
                    .printLoadingMeasure(MessageFormat.format("view: id: {0}, name: {1}", view.getKey(), view.getValue()))
                    .isSelectedView(view.getValue());

            //then:
            assertTrue(selected);
        }
    }

}
