package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ChangePointValueOnWatchListPageTest {

    @Parameterized.Parameters(name = "{index}: expected:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueOnWatchListPageTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;
    private static DataSourcePointIdentifier dataSourcePointIdentifier;
    private static WatchListPage watchListPageSubject;
    private static WatchListCriteria criteria;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(123);

        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);
        dataSourcePointIdentifier = dataSourcePointCriteria.getIdentifier();
        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        criteria = WatchListCriteria.criteria(dataSourcePointCriteria.getIdentifier());
        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, criteria);
        watchListPageSubject = watchListObjectsCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        if(watchListObjectsCreator != null)
            watchListObjectsCreator.deleteObjects();
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_change_point_value_on_watch_list() {

         //when:
        watchListPageSubject.selectWatchList(criteria.getIdentifier())
                 .openDataPointValueEditor(dataSourcePointIdentifier)
                 .setDataPointValue(dataSourcePointIdentifier, valueExpected)
                 .confirmDataPointValue(dataSourcePointIdentifier)
                 .closeEditorDataPointValue(dataSourcePointIdentifier);

         //and:
         String result = watchListPageSubject.waitOnPage(500)
                 .getDataPointValue(dataSourcePointIdentifier, valueExpected);

         //then:
         assertNotNull(result);
         assertNotEquals("", result);

         //then:
         assertEquals(valueExpected, result);
    }

}