package org.scadalts.e2e.test.impl.tests.check.datapoint;


import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.WatchListTestsUtil;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.scadalts.e2e.page.core.util.TypeParser.parseIntValueFormatted;

@Log4j2
@RunWith(E2eTestParameterizedRunner.class)
public class ChangePointValueInDetailsCheckTest {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public ChangePointValueInDetailsCheckTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static DataPointDetailsPage dataPointDetailsPageSubject;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        DataSourceCriteria dataSourceCriteria = new DataSourceCriteria(TestImplConfiguration.dataSourceName, DataSourceType.NONE, UpdatePeriodType.NONE);
        DataPointCriteria dataPointCriteria = new DataPointCriteria(TestImplConfiguration.dataPointName, DataPointType.NUMERIC, ChangeType.NO_CHANGE);
        WatchListCriteria watchListCriteria = new WatchListCriteria(dataSourceCriteria, dataPointCriteria);
        NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();

        WatchListTestsUtil watchListTestsUtil = new WatchListTestsUtil(navigationPage, watchListCriteria);
        dataPointDetailsPageSubject = watchListTestsUtil.getWatchListPage()
                .openDataPointDetails(watchListCriteria);
    }

    @Test
    public void test_change_data_point_value_in_details() {

        //when:
        dataPointDetailsPageSubject
                .setDataPointValue(valueExpected)
                .confirmDataPointValue();

        //and:
        String result = dataPointDetailsPageSubject.refreshPage().getDataPointValue();

        //then:
        assertNotNull(result);
        assertNotEquals("", result);

        result = String.valueOf(parseIntValueFormatted(result));

        //then:
        assertEquals(valueExpected, result);
    }
}