package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Test;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil.openGraphicalViews;

public class CreateGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();

    @After
    public void clean() {
        openGraphicalViews().openViewEditor(viewName)
                .delete();
    }

    @Test
    public void test_save_view() {
        //when:
        openGraphicalViews().openViewCreator()
                .chooseFile(GraphicalViewTestsUtil.BACKGROUND_FILE)
                .uploadFile()
                .setViewName(viewName)
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .save();

        //then:
        String body = openGraphicalViews().getBodyText();

        assertThat(body, containsString(viewName));
    }
}
