package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.scadalts.e2e.page.impl.pages.graphicalviews.EditViewPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@Log4j2
public class ChangeAlarmListCheckTest {

    private static final NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

    @Test
    public void test_check_no_changed_alarmList() {

        //when:
        EditViewPage edit = navigationPage.openGraphicalViews()
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
        EditViewPage edit = navigationPage.openGraphicalViews()
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
