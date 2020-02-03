package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import java.text.MessageFormat;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@Log4j2
public class ClickAllGraphicalViewCheckTest {

    @Test
    public void test_click_all_graphical_view() {
        //given:
        GraphicalViewsPage graphicalViewsPage = E2eAbstractRunnable.getNavigationPage().openGraphicalViews();
        Map<String, String> dataViews = graphicalViewsPage.getDataAllViews();

        for (Map.Entry<String, String> view : dataViews.entrySet()) {
            //when:
            boolean selected = graphicalViewsPage
                    .selectViewByName(view.getValue())
                    .printLoadingMeasure(MessageFormat.format("view: id: {0}, name: {1}", view.getKey(), view.getValue()))
                    .isSelectedView(view.getValue());

            //then:
            assertTrue(selected);
        }
    }

}
