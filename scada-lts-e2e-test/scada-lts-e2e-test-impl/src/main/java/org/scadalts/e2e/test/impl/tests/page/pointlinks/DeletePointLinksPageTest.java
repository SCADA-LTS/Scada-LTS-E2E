package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.AllObjectsForPointLinkTestsUtil;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(E2eTestParameterizedRunner.class)
public class DeletePointLinksPageTest {

    @Parameterized.Parameters(name = "{index}: script: {0}, {1}")
    public static Object[][] data() { return new Object[][] {
            {"return source.value;", EventType.CHANGE},
            {"return source.value;", EventType.UPDATE},
            {"", EventType.CHANGE},
            {"", EventType.UPDATE}
    };
    }

    private final String scriptExpected;
    private final EventType eventTypeExpected;

    public DeletePointLinksPageTest(String scriptExpected, EventType eventTypeExpected) {
        this.scriptExpected = scriptExpected;
        this.eventTypeExpected = eventTypeExpected;
    }

    private static AllObjectsForPointLinkTestsUtil allObjectsForPointLinkTestsUtil;
    private static PointLinksPage pointLinksPageSubject;
    private static PointLinkCriteria criteria;

    @BeforeClass
    public static void setup() {
        allObjectsForPointLinkTestsUtil = new AllObjectsForPointLinkTestsUtil(E2eAbstractRunnable.getNavigationPage());
        allObjectsForPointLinkTestsUtil.getDataSourcesAndPointsPageTestsUtil().createObjects();
        pointLinksPageSubject = allObjectsForPointLinkTestsUtil.openPage();
        criteria = allObjectsForPointLinkTestsUtil.getCriteria();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestsUtil.deleteObjects();
    }

    @Before
    public void createPointLink() {
        pointLinksPageSubject.reopen()
                .openPointLinkCreator()
                .setPoints(criteria)
                .setScript(scriptExpected)
                .setEventType(eventTypeExpected)
                .savePointLink();
    }

    @After
    public void deletePointLink() {
        allObjectsForPointLinkTestsUtil.getPointLinksTestsUtil().deleteObjects();
    }

    @Test
    public void test_delete_point_link() {

        //when:
        String pointLinksTableBeforeDelete = pointLinksPageSubject
                .getPointLinksTableText();

        //then:
        assertThat(pointLinksTableBeforeDelete, containsString(criteria.getIdentifier().getValue()));

        //and when:
        pointLinksPageSubject.openPointLinkEditor(criteria)
                .deletePointLink();

        //and:
        String pointLinksTableAfterDelete = pointLinksPageSubject.getPointLinksTableText();

        //then:
        assertThat(pointLinksTableAfterDelete, not(containsString(criteria.getIdentifier().getValue())));
    }
}
