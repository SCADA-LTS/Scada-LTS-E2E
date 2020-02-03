package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DeleteGraphicalViewTest {

    private final String viewName = "viewNameTest" + System.nanoTime();

    @Before
    public void createView() {
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

    @Test
    public void test_delete_view() {

        //when:
        GraphicalViewTestsUtil.openGraphicalViews()
                .openViewEditor(viewName)
                .delete();

        //then:
        String body = GraphicalViewTestsUtil.openGraphicalViews().getBodyText();

        assertThat(body, not(containsString(viewName)));
    }
}