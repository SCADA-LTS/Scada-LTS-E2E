package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.DataSourcePointTestObjectsUtil;
import org.scadalts.e2e.test.impl.utils.WatchListTestObjectsUtil;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(E2eTestParameterizedRunner.class)
public class ChangePointValueOnWatchListPageTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueOnWatchListPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static TestObjectsUtilible<WatchListPage, WatchListPage> watchListTestsUtil;
    private static TestObjectsUtilible<DataSourcesPage, EditDataSourceWithPointListPage> dataSourcesAndPointsPageTestsUtil;
    private static DataSourcePointCriteria dataSourcePointCriteria;
    private static WatchListPage watchListPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        dataSourcesAndPointsPageTestsUtil = new DataSourcePointTestObjectsUtil(navigationPage, dataSourcePointCriteria);
        dataSourcesAndPointsPageTestsUtil.createObjects();

        watchListTestsUtil = new WatchListTestObjectsUtil(navigationPage, dataSourcePointCriteria);
        watchListPageSubject = watchListTestsUtil.createObjects();
    }

    @AfterClass
    public static void clean() {
        watchListTestsUtil.deleteObjects();
        dataSourcesAndPointsPageTestsUtil.deleteObjects();
    }

    @Test
    public void test_change_point_value_on_watch_list() {

         //when:
        watchListPageSubject.openDataPointValueEditor(dataSourcePointCriteria)
                 .setDataPointValue(dataSourcePointCriteria, valueExpected)
                 .confirmDataPointValue(dataSourcePointCriteria)
                 .closeEditorDataPointValue(dataSourcePointCriteria);

         //and:
         String result = watchListPageSubject.waitOnPage(500)
                 .getDataPointValue(dataSourcePointCriteria, valueExpected);

         //then:
         assertNotNull(result);
         assertNotEquals("", result);

         //then:
         assertEquals(valueExpected, result);
    }

}