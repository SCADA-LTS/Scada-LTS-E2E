package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.junit.Assert.assertEquals;

@RunWith(TestParameterizedWithPageRunner.class)
public class ChangeValuePointsForFirstEnabledInIntervalMsBetweenPoints {

    @Parameterized.Parameters(name = "number of test: {index}, time interval [ms]: {0}")
    public static Integer[] data() {
        return new Integer[] {
                0, 10, 1000
        };
    }

    private final int interval;

    public ChangeValuePointsForFirstEnabledInIntervalMsBetweenPoints(int interval) {
        this.interval = interval;
    }

    private static DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private WatchListObjectsCreator watchListObjectsCreator;
    private WatchListPage watchListPage;

    private DataSourcePointIdentifier dataSourcePointIdentifier1;
    private DataSourcePointIdentifier dataSourcePointIdentifier2;
    private DataPointObjectsCreator dataPointObjectsCreator;
    private static NavigationPage navigationPage;

    @BeforeClass
    public static void createDataSource() {
        navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria);
        dataSourcePointObjectsCreator.createObjects();
    }

    @Before
    public void setUp() {

        DataPointCriteria point1 = DataPointCriteria.noChange(IdentifierObjectFactory.dataPointName(DataPointType.BINARY),"0", false);
        DataPointCriteria point2 = DataPointCriteria.noChange(IdentifierObjectFactory.dataPointName(DataPointType.BINARY), "0", false);

        dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage, dataSourceCriteria, point1, point2);
        EditDataSourceWithPointListPage page = dataPointObjectsCreator.createObjects();

        if(interval != 0) {
            page.enableDataPoint(point1.getIdentifier())
                    .waitOnPage(interval)
                    .enableDataPoint(point2.getIdentifier());

        } else {
            page.enableAllDataPoint();
        }

        dataSourcePointIdentifier1 = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                point1.getIdentifier());

        dataSourcePointIdentifier2 = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                point2.getIdentifier());

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(dataSourcePointIdentifier1, dataSourcePointIdentifier2));
        watchListObjectsCreator.createObjects();

        watchListPage = navigationPage.openWatchList();
    }

    @After
    public void clean() {
        if(watchListPage != null)
            watchListPage.acceptAlertOnPage();
        if(watchListObjectsCreator != null)
            watchListObjectsCreator.deleteObjects();
        if(dataPointObjectsCreator != null)
            dataPointObjectsCreator.deleteObjects();
    }

    @AfterClass
    public static void cleanAll() {
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_set_points() {

        //when:
        watchListPage.setValueWithoutCloseEditor(dataSourcePointIdentifier1, "1");

        //and:
        String alert = watchListPage.getAlertContent();
        watchListPage.closeEditorDataPointValue(dataSourcePointIdentifier1);

        //then:
        assertEquals("Failure because the following alert popped up:" + alert, "", alert);
    }

}
