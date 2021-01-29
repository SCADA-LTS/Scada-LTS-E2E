package org.scadalts.e2e.test.impl.tests.page.watchlist.pointdetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(TestWithPageRunner.class)
public class AnnotationsChangeValueIfPointDisabledPageTest {

    private CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;
    private DataSourcesPage dataSourcesPage;
    private WatchListPage watchListPage;
    private DataSourceCriteria dataSourceCriteria;
    private DataPointCriteria dataPointCriteria;
    private DataSourcePointCriteria dataSourcePointCriteria;
    private WatchListCriteria watchListCriteria;

    @Before
    public void createDataSourceAndPoint() {

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();

        dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
        dataPointCriteria = DataPointCriteria.noChange(DataPointType.NUMERIC, "123.0");
        dataSourcePointCriteria = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPointCriteria);

        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcesPage = dataSourcePointObjectsCreator.createObjects();

        watchListCriteria = WatchListCriteria.criteria(dataSourcePointCriteria.getIdentifier());
        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        watchListPage = watchListObjectsCreator.createObjects();
    }

    @After
    public void clean() {
        if(Objects.nonNull(watchListObjectsCreator))
            watchListObjectsCreator.deleteObjects();
        if(Objects.nonNull(dataSourcePointObjectsCreator))
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_annotation_is_visible_if_point_disabled() {

        //given:
        String value = "7777";
        String expectAnnotation = MessageFormat.format("User: {0}", E2eConfiguration.userName);

        //when:
        watchListPage.reopen()
                .selectWatchList(watchListCriteria.getIdentifier())
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .setDataPointValue(value)
                .confirmDataPointValue()
                .waitDataPointValue(value);

        //and:
        dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointEditor(dataPointCriteria.getIdentifier())
                .disableDataPoint(dataPointCriteria.getIdentifier());

        //then:
        String result = watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .getAnnotationFirstFromHistory();

        assertNotNull(result);
        assertEquals(expectAnnotation, result);
    }

    @Test
    public void test_annotation_is_visible_if_point_disabled_and_enabled() {

        //given:
        String value = "5555";
        String expectAnnotation = MessageFormat.format("User: {0}", E2eConfiguration.userName);

        //when:
        watchListPage.reopen()
                .selectWatchList(watchListCriteria.getIdentifier())
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .setDataPointValue(value)
                .confirmDataPointValue()
                .waitDataPointValue(value);

        //and:
        dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointEditor(dataPointCriteria.getIdentifier())
                .disableDataPoint(dataPointCriteria.getIdentifier())
                .enableDataPoint(dataPointCriteria.getIdentifier());


        //then:
        List<String> result = watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .getAnnotationsFromHistory(1);

        assertNotNull(result);
        assertEquals(expectAnnotation, result.get(0));
    }

    @Test
    public void test_annotation_is_visible_if_point_disabled_and_enabled_and_change_value() {

        //given:
        String value = "5555";
        String value2 = "6666";
        String expectAnnotation = MessageFormat.format("User: {0}", E2eConfiguration.userName);
        List<String> expectAnnotations = new ArrayList<>();
        expectAnnotations.add(expectAnnotation);
        expectAnnotations.add(expectAnnotation);

        watchListPage.reopen()
                .selectWatchList(watchListCriteria.getIdentifier())
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .setDataPointValue(value)
                .confirmDataPointValue()
                .waitDataPointValue(value);

        //when:
        dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointEditor(dataPointCriteria.getIdentifier())
                .disableDataPoint(dataPointCriteria.getIdentifier())
                .enableDataPoint(dataPointCriteria.getIdentifier());

        //and:
        watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .setDataPointValue(value2)
                .confirmDataPointValue()
                .waitDataPointValue(value2);

        //then:
        List<String> result = watchListPage.reopen()
                .openDataPointDetails(dataSourcePointCriteria.getIdentifier())
                .getAnnotationsFromHistory(2);

        assertNotNull(result);
        assertNotEquals(Collections.emptyList(), result);
        assertEquals(expectAnnotations, result);
    }
}
