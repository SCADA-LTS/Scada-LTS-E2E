package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.SourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.scadalts.e2e.page.core.util.TypeParser.parseIntValueFormatted;

@RunWith(E2eTestParameterizedRunner.class)
public class ChangePointValueOnWatchListPageTest {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueOnWatchListPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static WatchListTestsUtil watchListTestsUtil;
    private static SourcePointCriteria sourcePointCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecound();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        sourcePointCriteria = new SourcePointCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        watchListTestsUtil = new WatchListTestsUtil(navigationPage, sourcePointCriteria);
        watchListTestsUtil.init();
        watchListPageSubject = watchListTestsUtil.addWatchLists();
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.clean();
    }

    @Test
    public void test_change_point_value_on_watch_list() {

         //when:
        watchListPageSubject.openDataPointValueEditor(sourcePointCriteria)
                 .setDataPointValue(sourcePointCriteria, valueExpected)
                 .confirmDataPointValue(sourcePointCriteria)
                 .closeEditorDataPointValue(sourcePointCriteria);

         //and:
         String result = watchListPageSubject.getDataPointValue(sourcePointCriteria);

         //then:
         assertNotNull(result);
         assertNotEquals("", result);

         result = String.valueOf(parseIntValueFormatted(result));

         //then:
         assertEquals(valueExpected, result);
    }

}