package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
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

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.EXPECTED_X_ALARMS_STORUNGS;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class GetAlarmsLivePaginationServiceTest {

    @Parameterized.Parameters(name = "{index}: offset: {0}, limit: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {0, 10},
                {1, 9},
                {2, 8},
                {3, 7},
                {4, 6},
                {5, 5},
                {6, 4},
                {7, 3},
                {8, 2},
                {9, 1},
                {10, 0},
                {0, 2},
                {2, 2},
                {4, 2},
                {6, 2},
                {8, 2},
        };
    }

    private final int offset;
    private final int limit;

    public GetAlarmsLivePaginationServiceTest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private static WatchListObjectsCreator watchListObjectsCreator;
    private static List<AlarmResponse> getResult10;

    @BeforeClass
    public static void setup() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        DataPointIdentifier[] idnetifiers = new DataPointIdentifier[] {

                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),

                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
        };

        DataPointCriteria[] points = Stream.of(idnetifiers)
                .map(a -> DataPointCriteria.noChange(a, "0"))
                .toArray(a -> new DataPointCriteria[10]);

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, points);
        dataSourcePointObjectsCreator.createObjects();

        DataSourcePointIdentifier[] identifiers = Stream.of(points)
                .map(a -> new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(), a.getIdentifier()))
                .toArray(a -> new DataSourcePointIdentifier[10]);

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(identifiers));
        watchListObjectsCreator.createObjects();

        WatchListPage watchListPage = navigationPage.openWatchList();

        Stream.of(identifiers).forEach(a -> {
            watchListPage.setValue(a, "1");
        });

        PaginationParams pagination10 = PaginationParams.builder()
                .limit(10)
                .offset(0)
                .build();

        //when:
        E2eResponse<List<AlarmResponse>> getResponse10 = TestWithoutPageUtil.getLiveAlarms(pagination10,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        getResult10 = getResponse10.getValue();

        String msg = MessageFormat.format(EXPECTED_X_ALARMS_STORUNGS, "10");

        //then:
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse10.getStatus());
        assertNotNull(getResult10);
        assertEquals(msg, 10, getResult10.size());
    }

    @AfterClass
    public static void clean() {

        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_get_live_alarms_limit() {

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

    @Test
    public void test_get_live_alarms_offset() {

        //given:
        PaginationParams paginationToTest = PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build();

        //and when:
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationToTest,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        List<AlarmResponse> getResult = getResponse.getValue();

        //then:
        assertEquals(200, getResponse.getStatus());
        assertNotNull(getResult);
        assertEquals(getResult10.subList(offset, offset + limit), getResult);
    }

}
