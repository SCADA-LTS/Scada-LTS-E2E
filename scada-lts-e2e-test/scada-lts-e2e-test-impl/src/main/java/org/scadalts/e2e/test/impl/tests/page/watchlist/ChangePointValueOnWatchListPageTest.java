package org.scadalts.e2e.test.impl.tests.page.watchlist;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.DataSourcesAndPointsPageTestsUtil;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.scadalts.e2e.page.core.util.TypeParser.parseIntValueFormatted;

@Log4j2
@RunWith(E2eTestParameterizedRunner.class)
public class ChangePointValueOnWatchListPageTest {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection<String> data() {
        return WatchListTestsUtil.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueOnWatchListPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static DataSourcesAndPointsPageTestsUtil dataSourcesPageTestsUtil;
    private static WatchListTestsUtil watchListTestsUtil;
    private static WatchListCriteria watchListCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourcesAndPointsPageTestsUtil.createDataSourceCriteria();
        DataPointCriteria dataPointCriteria = new DataPointCriteria("dp_test" + System.nanoTime(), DataPointType.NUMERIC, ChangeType.NO_CHANGE);
        watchListCriteria = new WatchListCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        dataSourcesPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(navigationPage, dataSourceCriteria, dataPointCriteria);
        dataSourcesPageTestsUtil.init("123");

        watchListTestsUtil = new WatchListTestsUtil(navigationPage, watchListCriteria);
        watchListPageSubject = watchListTestsUtil.getWatchListPage()
                .addToWatchList(watchListCriteria);
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.clean();
        dataSourcesPageTestsUtil.clean();
    }

    @Test
    public void test_change_point_value_on_watch_list() {

         //when:
        watchListPageSubject.openEditorDataPointValue(watchListCriteria)
                 .setDataPointValue(watchListCriteria, valueExpected)
                 .confirmDataPointValue(watchListCriteria)
                 .closeEditorDataPointValue(watchListCriteria);

         //and:
         String result = watchListPageSubject.getDataPointValue(watchListCriteria);

         //then:
         assertNotNull(result);
         assertNotEquals("", result);

         result = String.valueOf(parseIntValueFormatted(result));

         //then:
         assertEquals(valueExpected, result);
    }

}