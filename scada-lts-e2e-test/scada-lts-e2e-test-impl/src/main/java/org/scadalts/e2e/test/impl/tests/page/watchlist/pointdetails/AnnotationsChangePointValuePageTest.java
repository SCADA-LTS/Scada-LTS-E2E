package org.scadalts.e2e.test.impl.tests.page.watchlist.pointdetails;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointDetailsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.ListLimitedSupportedAddMethod;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(TestParameterizedWithPageRunner.class)
public class AnnotationsChangePointValuePageTest {

    @Parameterized.Parameters(name = "{index}: expected:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String value;
    private final String userExpected;

    public AnnotationsChangePointValuePageTest(String value) {
        this.value = value;
        this.userExpected = E2eConfiguration.userName;
    }

    private static CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;
    private static DataPointDetailsPage dataPointDetailsPageSubject;
    private static ListLimitedSupportedAddMethod<String> listExpected;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPointCriteria = DataPointCriteria.noChange(DataPointType.NUMERIC, "123.0");
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(dataSourcePointCriteria.getIdentifier());
        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        dataPointDetailsPageSubject = watchListObjectsCreator.createObjects()
                .selectWatchList(watchListCriteria.getIdentifier())
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier());

        int limit = dataPointDetailsPageSubject.getHistoryLimit();
        List<String> result = dataPointDetailsPageSubject.getAnnotationsFromHistory();
        List<String> values = dataPointDetailsPageSubject.getValuesFromHistory();

        listExpected = new ListLimitedSupportedAddMethod<>(limit);
        listExpected.addAll(result, values);
    }

    @AfterClass
    public static void clean() {
        if(Objects.nonNull(watchListObjectsCreator))
            watchListObjectsCreator.deleteObjects();
        if(Objects.nonNull(dataSourcePointObjectsCreator))
            dataSourcePointObjectsCreator.deleteObjects();
        if(Objects.nonNull(listExpected))
            listExpected.clear();
    }

    @Test
    public void test_annotation_is_visible_if_user_change_point() {

        //given:
        listExpected.addUnique(MessageFormat.format("User: {0}", userExpected), value);

        //when:
        dataPointDetailsPageSubject.setDataPointValue(value)
                .confirmDataPointValue()
                .waitDataPointValue(value);

        //and:
        List<String> result = dataPointDetailsPageSubject.refreshPage().getAnnotationsFromHistory();

        //then:
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(listExpected, result);
    }
}
