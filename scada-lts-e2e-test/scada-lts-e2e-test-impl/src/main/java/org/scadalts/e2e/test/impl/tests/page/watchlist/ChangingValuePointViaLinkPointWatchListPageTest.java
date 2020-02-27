package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.util.TypeParser;
import org.scadalts.e2e.page.impl.dict.EventType;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.tests.page.pointlinks.AbstractPointLinksPageTest;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(E2eTestParameterizedRunner.class)
public class ChangingValuePointViaLinkPointWatchListPageTest extends AbstractPointLinksPageTest {

    @Parameterized.Parameters(name = "{index}: script: {0}, {1}, args: {2}, {3}, {4}")
    public static List<Object[]> data() { return Arrays.asList(
            new Object[]{"return source.value;", EventType.CHANGE, 123435, 1233, 43645},
            new Object[]{"return source.value;", EventType.UPDATE, 123435, 1233, 43645}
        );
    }

    private final String scriptExpected;
    private final EventType eventTypeExpected;
    private final int[] values;

    public ChangingValuePointViaLinkPointWatchListPageTest(String scriptExpected, EventType eventTypeExpected, int arg1, int arg2, int arg3) {
        this.scriptExpected = scriptExpected;
        this.eventTypeExpected = eventTypeExpected;
        this.values = new int[]{arg1, arg2, arg3};
    }

    private WatchListPage watchListPageSubject;
    private WatchListTestsUtil watchListTestsUtil;

    @Before
    public void addPointLinkAndWatchList() {

        getPointLinksPage().openPointLinkCreator()
                .setPoints(getCriteria())
                .setScript(scriptExpected)
                .setEventType(eventTypeExpected)
                .savePointLink();

        watchListTestsUtil = new WatchListTestsUtil(E2eAbstractRunnable.getNavigationPage(),
                getSource(), getTarget());

        watchListPageSubject = watchListTestsUtil.addWatchLists();
    }

    @After
    public void cleanWatchList() {
        watchListTestsUtil.clean();
    }

    @Test
    public void test_point_links() {

        for (int valueExpected : values) {

            //given:
            String valueSettable = String.valueOf(valueExpected);

            //when:
            watchListPageSubject.openDataPointValueEditor(getSource())
                    .setDataPointValue(getSource(), valueSettable)
                    .confirmDataPointValue(getSource())
                    .closeEditorDataPointValue(getSource());

            String result = watchListPageSubject.getDataPointValue(getTarget());
            int resultRaw = TypeParser.parseIntValueFormatted(result);

            //then:
            assertEquals(valueExpected, resultRaw);
        }

    }
}
