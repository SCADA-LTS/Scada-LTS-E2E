package org.scadalts.e2e.test.impl.tests.page.datasource.datapoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
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
import org.scadalts.e2e.test.impl.utils.ListLimitedOnlyMethodAddSupported;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(TestParameterizedWithPageRunner.class)
public class AnnotationChangePointValuePageTest {

    @Parameterized.Parameters(name = "{index}: expected:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String value;
    private final String userExpected;

    public AnnotationChangePointValuePageTest(String value) {
        this.value = value;
        this.userExpected = E2eConfiguration.userName;
    }

    private static CreatorObject<WatchListPage, WatchListPage> watchListTestsUtil;
    private static CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesAndPointsPageTestsUtil;
    private static DataPointDetailsPage dataPointDetailsPageSubject;
    private static DataSourcesPage dataSourcesPage;
    private static WatchListPage watchListPage;
    private static DataSourceCriteria dataSourceCriteria;
    private static DataPointCriteria dataPointCriteria;
    private static DataSourcePointCriteria dataSourcePointCriteria;
    private static ListLimitedOnlyMethodAddSupported<String> listExpected;

    @BeforeClass
    public static void createDataSourceAndPoint() {

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();

        dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataPointCriteria = DataPointCriteria.noChange(DataPointType.NUMERIC, "123.0");
        dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);

        dataSourcesAndPointsPageTestsUtil = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcesPage = dataSourcesAndPointsPageTestsUtil.createObjects();

        watchListTestsUtil = new WatchListObjectsCreator(navigationPage, dataSourcePointCriteria);
        watchListPage = watchListTestsUtil.createObjects();
        dataPointDetailsPageSubject = watchListPage
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier());

        int limit = dataPointDetailsPageSubject.getHistoryLimit();
        List<String> result = dataPointDetailsPageSubject.getAnnotationsFromHistory();

        listExpected = new ListLimitedOnlyMethodAddSupported<>(limit);
        listExpected.addAll(result);
    }

    @AfterClass
    public static void clean() {
        if(Objects.nonNull(watchListTestsUtil))
            watchListTestsUtil.deleteObjects();
        if(Objects.nonNull(dataSourcesAndPointsPageTestsUtil))
            dataSourcesAndPointsPageTestsUtil.deleteObjects();
        if(Objects.nonNull(listExpected))
            listExpected.clear();
    }

    @Test
    public void test_annotation_is_visible_if_user_change_point() {

        //given:
        listExpected.add(MessageFormat.format("User: {0}", userExpected));

        //when:
        watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .setDataPointValue(value)
                .confirmDataPointValue()
                .waitDataPointValue(value);

        //and:
        List<String> result = dataPointDetailsPageSubject.refreshPage().getAnnotationsFromHistory();

        //then:
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(listExpected, result);
    }


    @Test
    public void test_annotation_is_visible_if_point_disabled() {

        //when:
        dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointEditor(dataPointCriteria.getIdentifier())
                .disableDataPoint(dataPointCriteria.getIdentifier());


        //and:
        List<String> result = watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .getAnnotationsFromHistory();

        //then:
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(listExpected, result);
    }

    @Test
    public void test_annotation_is_visible_if_point_disabled_and_enabled() {

        //when:
        dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointEditor(dataPointCriteria.getIdentifier())
                .disableDataPoint(dataPointCriteria.getIdentifier())
                .enableDataPoint(dataPointCriteria.getIdentifier());


        //and:
        List<String> result = watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .getAnnotationsFromHistory();

        //then:
        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(listExpected, result);
    }
}