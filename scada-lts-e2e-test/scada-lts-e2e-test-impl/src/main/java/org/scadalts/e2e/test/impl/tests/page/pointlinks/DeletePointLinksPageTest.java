package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.dict.EventType;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(E2eTestParameterizedRunner.class)
public class DeletePointLinksPageTest extends AbstractPointLinksPageTest {


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

    @Before
    public void createPointLink() {
        getPointLinksPage().openPointLinkCreator()
                .setPoints(getCriteria())
                .setScript(scriptExpected)
                .setEventType(eventTypeExpected)
                .savePointLink();
    }

    @Test
    public void test_delete_point_link() {

        //when:
        String pointLinksTableBeforeDelete = getPointLinksPage().reopen()
                .getPointLinksTableText();

        //then:
        assertThat(pointLinksTableBeforeDelete, containsString(getCriteria().getIdentifier()));

        //and when:
        getPointLinksPage().openPointLinkEditor(getCriteria())
                .deletePointLink();

        //and:
        String pointLinksTableAfterDelete = getPointLinksPage().reopen()
                .getPointLinksTableText();

        //then:
        assertThat(pointLinksTableAfterDelete, not(containsString(getCriteria().getIdentifier())));
    }
}
