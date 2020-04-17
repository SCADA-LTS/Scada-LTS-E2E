package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ClickAllGraphicalViewCheckTest {

    @Parameterized.Parameters(name = "{index}: id: {0}, viewName: {1}")
    public static List<String[]> data() {
        graphicalViewsPage = TestWithPageUtil.preparingTest()
                .openGraphicalViews();
        return graphicalViewsPage.getDataAllViews()
                .entrySet()
                .stream()
                .map(a -> new String[] {a.getKey(), a.getValue()})
                .collect(Collectors.toList());
    }

    private static GraphicalViewsPage graphicalViewsPage;
    private final String id;
    private final GraphicalViewIdentifier viewName;

    public ClickAllGraphicalViewCheckTest(String id, String viewName) {
        this.id = id;
        this.viewName = new GraphicalViewIdentifier(viewName);
    }

    @BeforeClass
    public static void setup() {
        graphicalViewsPage.reopen();
    }

    @AfterClass
    public static void close() {
        graphicalViewsPage.acceptAlertOnPage();
    }

    @Test
    public void test_click_all_graphical_view() {

        boolean selected = graphicalViewsPage
                .selectViewByName(viewName)
                .waitOnLoadedBackground()
                .printLoadingMeasure(MessageFormat.format("view: id: {0}, name: {1}", id, viewName.getValue()))
                .isSelectedView(viewName);

        //then:
        assertTrue(_genMsg(viewName, id), selected);
    }

    private static String _genMsg(GraphicalViewIdentifier identifier, String id) {
        return MessageFormat.format("Expected view selected: {0}, id: {1}", identifier.getValue(), id);
    }
}
