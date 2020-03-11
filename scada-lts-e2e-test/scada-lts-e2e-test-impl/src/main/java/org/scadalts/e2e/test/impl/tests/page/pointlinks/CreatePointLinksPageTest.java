package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksDetailsPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class CreatePointLinksPageTest {

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

    public CreatePointLinksPageTest(String scriptExpected, EventType eventTypeExpected) {
        this.scriptExpected = scriptExpected;
        this.eventTypeExpected = eventTypeExpected;
    }

    private static AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private static PointLinksPage pointLinksPageSubject;
    private static PointLinkCriteria criteria;
    private static DataSourcePointIdentifier sourceIdentifier;
    private static DataSourcePointIdentifier targetIdentifier;

    @BeforeClass
    public static void setup() {
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage());
        allObjectsForPointLinkTestCreator.getDataSourcesAndPointsPageTestsCreator().createObjects();
        pointLinksPageSubject = allObjectsForPointLinkTestCreator.openPage();
        criteria = allObjectsForPointLinkTestCreator.getCriteria();
        sourceIdentifier = criteria.getSource().getIdentifier();
        targetIdentifier = criteria.getTarget().getIdentifier();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
    }

    @After
    public void deletePointLink() {
        allObjectsForPointLinkTestCreator.getPointLinksTestsCreator()
                .deleteObjects();
    }

    @Test
    public void test_create_point_link() {

        //when:
        pointLinksPageSubject.reopen()
                .openPointLinkCreator()
                .setPoints(criteria)
                .setScript(scriptExpected)
                .setEventType(eventTypeExpected)
                .savePointLink();

        //and:
        String pointLinksTable = pointLinksPageSubject.getPointLinksTableText();

        //then:
        assertThat(pointLinksTable, containsString(criteria.getIdentifier().getValue()));

        //and when:
        PointLinksDetailsPage page = pointLinksPageSubject.openPointLinkEditor(criteria);

        //then:
        assertEquals(scriptExpected, page.getScript());
        assertEquals(eventTypeExpected, page.getEventType());
        assertEquals(sourceIdentifier.getValue(), page.getSourcePointIdText());
        assertEquals(targetIdentifier.getValue(), page.getTargetPointIdText());
    }
}
