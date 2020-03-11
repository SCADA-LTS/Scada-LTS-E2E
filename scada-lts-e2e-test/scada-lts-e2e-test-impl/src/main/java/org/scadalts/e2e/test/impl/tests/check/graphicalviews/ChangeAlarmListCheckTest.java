package org.scadalts.e2e.test.impl.tests.check.graphicalviews;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.EditGraphicalViewPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@Ignore
@RunWith(TestWithPageRunner.class)
public class ChangeAlarmListCheckTest {

    private final NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
    private final GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(new GraphicalViewIdentifier(TestImplConfiguration.graphicalViewName));
    private final GraphicalViewObjectsCreator graphicalViewObjectsCreator = new GraphicalViewObjectsCreator(navigationPage, criteria);
    private GraphicalViewsPage editViewPageSubject;

    @Before
    public void setup() {
        editViewPageSubject = graphicalViewObjectsCreator.openPage();
    }

    @Test
    public void test_check_no_changed_alarmList() {

        //when:
        EditGraphicalViewPage edit = editViewPageSubject.openViewEditor(criteria);

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
        EditGraphicalViewPage edit = editViewPageSubject.openViewEditor(criteria);

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
