package org.scadalts.e2e.test.impl.tests.check.datapoint;


import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.ListLimitedSupportedAddMethod;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@Log4j2
@RunWith(Parameterized.class)
public class SequencePointValueHistoryInDetailsCheckTest {

    @Parameterized.Parameters(name = "{index}:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String valueExpected;

    public SequencePointValueHistoryInDetailsCheckTest(String valueExpected) {
        this.valueExpected = valueExpected;
    }

    private static DataPointDetailsPage dataPointDetailsPageSubject;
    private static ListLimitedSupportedAddMethod<String> listExcepted;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.criteriaSecond(new DataSourceIdentifier(TestImplConfiguration.dataSourceName, DataSourceType.VIRTUAL_DATA_SOURCE));
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(new DataPointIdentifier(TestImplConfiguration.dataPointName, DataPointType.NUMERIC));
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);

        WatchListIdentifier identifier = new WatchListIdentifier(TestImplConfiguration.watchListName);
        dataPointDetailsPageSubject = navigationPage.openWatchList()
                .selectWatchList(identifier)
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier());

        int limit = dataPointDetailsPageSubject.getHistoryLimit();
        List<String> result = dataPointDetailsPageSubject.getValuesFromHistory();

        listExcepted = new ListLimitedSupportedAddMethod<>(limit);
        listExcepted.addAll(result);
    }

    @AfterClass
    public static void clean() {
        if(listExcepted != null)
            listExcepted.clear();
    }

    @Test
    public void test_sequence_history_change_point_value() {

        //given:
        listExcepted.addUnique(valueExpected);

        //when:
        dataPointDetailsPageSubject
                .setDataPointValue(valueExpected)
                .confirmDataPointValue()
                .getDataPointValue(valueExpected);

        //and:
        List<String> result = dataPointDetailsPageSubject.getValuesFromHistory();

        //then:
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(listExcepted, result);
    }

}