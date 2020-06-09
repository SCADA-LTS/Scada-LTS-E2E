package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class GetLiveAlarmsServiceTest {

    @Parameterized.Parameters(name = "{index}: offset: {0}, limit: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {0, 10}
        };
    }

    private final int offset;
    private final int limit;

    public GetLiveAlarmsServiceTest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private WatchListObjectsCreator watchListObjectsCreator;
    private DataPointIdentifier[] idnetifiers;

    @Before
    public void setup() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        idnetifiers = new DataPointIdentifier[] {
                new DataPointIdentifier("Te AL Test1", DataPointType.BINARY),
                new DataPointIdentifier("Te AL Test2", DataPointType.BINARY),
                new DataPointIdentifier("Te AL Test3", DataPointType.BINARY),
                new DataPointIdentifier("Fe AL Test4", DataPointType.BINARY),
                new DataPointIdentifier("Fe AL Test5", DataPointType.BINARY),

                new DataPointIdentifier("Fe ST Test1", DataPointType.BINARY),
                new DataPointIdentifier("Ga ST Test2", DataPointType.BINARY),
                new DataPointIdentifier("Da ST Test3", DataPointType.BINARY),
                new DataPointIdentifier("Da ST Test4", DataPointType.BINARY),
                new DataPointIdentifier("Da ST Test5", DataPointType.BINARY),
        };

        DataPointCriteria[] points = Stream.of(idnetifiers).map(a -> DataPointCriteria.noChange(a, "0"))
                .toArray(a -> new DataPointCriteria[10]);

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, points);

        dataSourcePointObjectsCreator.createObjects();

        DataSourcePointCriteria[] dataSourcePoints = Stream.of(points).map(a -> DataSourcePointCriteria.criteria(dataSourceCriteria, a))
                .toArray(a -> new DataSourcePointCriteria[10]);
        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, dataSourcePoints);
        watchListObjectsCreator.createObjects();

        WatchListPage watchListPage = navigationPage.openWatchList();
        Stream.of(idnetifiers).forEach(a -> {
            DataSourcePointIdentifier identifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(), a);
            watchListPage.openDataPointValueEditor(identifier)
                    .setDataPointValue(identifier,  "1")
                    .confirmDataPointValue(identifier)
                    .closeEditorDataPointValue(identifier);
        });


    }

    @After
    public void clean() {
        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_get_live_alarms() {

            //given:
            PaginationParams paginationParams = PaginationParams.builder()
                    .limit(limit)
                    .offset(offset)
                    .build();

            //when:
            E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                    TestImplConfiguration.waitingAfterSetPointValueMs);
            List<AlarmResponse> getResult = getResponse.getValue();

            //then:
            assertEquals(200, getResponse.getStatus());
            assertNotNull(getResult);
            assertEquals(limit, getResult.size());
    }
}
