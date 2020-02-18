package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.graphicalviews.EditViewPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.GraphicalViewTestsUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(E2eTestRunner.class)
public class ChangeAlarmListCheckTest {

    private final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final GraphicalViewTestsUtil testsUtil = new GraphicalViewTestsUtil(navigationPage, "");

    @Test
    @Ignore
    public void test_check_no_changed_alarmList() {

        //when:
        EditViewPage edit = testsUtil.openGraphicalViews()
                .acceptAlertIfExists()
                .openViewEditor(TestImplConfiguration.graphicalViewName);

        //and:
        String alarmList = edit.getFirstAlarmListText();

        //then:
        assertNotEquals("", alarmList);

        //and when:
        String result = edit.waitOnPage(TestImplConfiguration.alarmListNoChangedAfterMs)
                .getFirstAlarmListText();

        //then:
        assertEquals(alarmList, result);

    }

    @Test
    public void test_check_changed_alarmList_c1Id() {

        //when:
        EditViewPage edit = testsUtil.openGraphicalViews()
                .acceptAlertIfExists()
                .openViewEditor(TestImplConfiguration.graphicalViewName);

        //and:
        String alarmList = edit.getFirstAlarmListText();

        //then:
        assertNotEquals("", alarmList);

        //and when:
        String result = edit.waitOnPage(TestImplConfiguration.alarmListChangedAfterMs)
                .getFirstAlarmListText();

        //then:
        assertNotEquals(alarmList, result);

    }
}
