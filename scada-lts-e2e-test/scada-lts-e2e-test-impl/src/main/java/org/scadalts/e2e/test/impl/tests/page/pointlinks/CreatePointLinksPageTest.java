package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.dict.EventType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksDetailsPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j2
@RunWith(E2eTestParameterizedRunner.class)
public class CreatePointLinksPageTest extends AbstractPointLinksPageTest {

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

    @After
    public void cleanAfterTest() {
        getPointLinksTestsUtil().cleanPointLink();
    }

    @Test
    public void test_create_point_link() {

        //when:
        PointLinksPage pointLinksPage = getPointLinksPage().reopen()
                .openPointLinkCreator()
                .setPoints(getCriteria())
                .setScript(scriptExpected)
                .setEventType(eventTypeExpected)
                .savePointLink();

        //and:
        String pointLinksTable = pointLinksPage.getPointLinksTableText();

        //then:
        assertThat(pointLinksTable, containsString(getCriteria().getIdentifier()));

        //and when:
        PointLinksDetailsPage page = pointLinksPage.openPointLinkEditor(getCriteria());
/*
        //then:
        assertEquals(scriptExpected, page.getScript());
        assertEquals(eventTypeExpected, page.getEvent());
        assertEquals(getSource().getIdentifier(), page.getSourcePointIdText());
        assertEquals(getTarget().getIdentifier(), page.getTargetPointIdText());*/
    }
}
