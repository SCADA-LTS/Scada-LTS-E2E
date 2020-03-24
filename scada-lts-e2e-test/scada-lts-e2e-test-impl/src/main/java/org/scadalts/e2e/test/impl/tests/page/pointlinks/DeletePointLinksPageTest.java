package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(TestParameterizedWithPageRunner.class)
public class DeletePointLinksPageTest {

    @Parameterized.Parameters(name = "{index}: script: {0}, {1}")
    public static Object[][] data() { return new Object[][] {
            {Script.sourceValue(), EventType.CHANGE},
            {Script.sourceValue(), EventType.UPDATE},
            {Script.empty(), EventType.CHANGE},
            {Script.empty(), EventType.UPDATE}
    };
    }

    private final Script scriptExpected;
    private final EventType eventTypeExpected;

    public DeletePointLinksPageTest(Script scriptExpected, EventType eventTypeExpected) {
        this.scriptExpected = scriptExpected;
        this.eventTypeExpected = eventTypeExpected;
    }

    private static AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private static PointLinksPage pointLinksPageSubject;
    private static PointLinkCriteria criteria;

    @BeforeClass
    public static void setup() {
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage());
        allObjectsForPointLinkTestCreator.getDataSourcesAndPointsPageTestsCreator().createObjects();
        pointLinksPageSubject = allObjectsForPointLinkTestCreator.openPage();
        criteria = allObjectsForPointLinkTestCreator.getCriteria();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
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
        allObjectsForPointLinkTestCreator.getPointLinksTestsCreator().deleteObjects();
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
