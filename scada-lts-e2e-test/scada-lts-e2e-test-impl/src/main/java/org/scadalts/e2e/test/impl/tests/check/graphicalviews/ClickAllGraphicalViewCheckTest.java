package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(E2eTestParameterizedRunner.class)
public class ClickAllGraphicalViewCheckTest {

    @Parameterized.Parameters(name = "{index}: id: {0}, viewName: {1}")
    public static List<String[]> data() {
        if(!E2eAbstractRunnable.isLogged())
            E2eAbstractRunnable.setup();
        GraphicalViewTestsUtil testsUtil = new GraphicalViewTestsUtil(E2eAbstractRunnable.getNavigationPage(), "");
        graphicalViewsPage = testsUtil.getGraphicalViewsPage();
        return graphicalViewsPage.getDataAllViews()
                .entrySet()
                .stream()
                .map(a -> new String[] {a.getKey(), a.getValue()})
                .collect(Collectors.toList());
    }

    private static GraphicalViewsPage graphicalViewsPage;
    private final String id;
    private final String viewName;

    public ClickAllGraphicalViewCheckTest(String id, String viewName) {
        this.id = id;
        this.viewName = viewName;
    }

    @Test
    public void test_click_all_graphical_view() {

        boolean selected = graphicalViewsPage
                .selectViewByName(viewName)
                .waitOnLoadedBackground()
                .printLoadingMeasure(MessageFormat.format("view: id: {0}, name: {1}", id, viewName))
                .isSelectedView(viewName);

        //then:
        assertTrue(selected);
    }

}
