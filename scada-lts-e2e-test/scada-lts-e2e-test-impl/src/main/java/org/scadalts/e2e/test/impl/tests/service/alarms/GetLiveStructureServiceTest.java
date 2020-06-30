package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;

import java.util.List;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class GetLiveStructureServiceTest {

    @Parameterized.Parameters(name = "{index}: offset: {0}, limit: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {0, 10}, {1, 9}, {2, 8}, {3, 7}, {4, 6}, {5, 5}, {6, 4}, {7, 3},
                {8, 2}, {9, 1}, {10, 0}, {0, 2}, {2, 2}, {4, 2}, {6, 2}, {8, 2},
                {0, 3}, {3, 3}, {6, 3}, {9, 1}, {0, 4}, {4, 4}, {8, 2}, {0, 5},
                {5, 5}, {0, 6}, {6, 4}, {0, 7}, {7, 3}, {0, 8}, {8, 2}, {0, 9}
        };
    }

    private final int offset;
    private final int limit;

    public GetLiveStructureServiceTest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private static WatchListObjectsCreator watchListObjectsCreator;
    private static List<AlarmResponse> getResult10;

    @BeforeClass
    public static void setup() {
/*
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        DataPointIdentifier[] activeIdnetifiers = new DataPointIdentifier[] {

                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),

                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
        };

        DataPointIdentifier[] inactiveIdnetifiers = new DataPointIdentifier[] {

                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),

                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
        };

        DataPointCriteria[] points = Stream.concat(Arrays.asList(activeIdnetifiers).stream(),
                Arrays.asList(inactiveIdnetifiers).stream())
                .map(a -> DataPointCriteria.noChange(a, "0"))
                .toArray(a -> new DataPointCriteria[12]);

        DataPointCriteria[] points = Stream.concat(Arrays.asList(activeIdnetifiers).stream(),
                Arrays.asList(inactiveIdnetifiers).stream())
                .map(a -> DataPointCriteria.noChange(a, "0"))
                .toArray(a -> new DataPointCriteria[12]);


        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, points);
        dataSourcePointObjectsCreator.createObjects();

        DataSourcePointIdentifier[] identifiers = Stream.of(points)
                .map(a -> new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(), a.getIdentifier()))
                .toArray(a -> new DataSourcePointIdentifier[12]);

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(identifiers));
        watchListObjectsCreator.createObjects();

        WatchListPage watchListPage = navigationPage.openWatchList();

        Stream.of(activeIdnetifiers).forEach(a -> {
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
        assertEquals(msg, 10, getResult10.size());*/
    }

    @AfterClass
    public static void clean() {

        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_structure_active_alarms_live() {


    }

    @Test
    public void test_structure_inactive_alarms_live() {


    }

    @Test
    public void test_structure_active_storungs_live() {


    }

    @Test
    public void test_structure_inactive_storungs_live() {


    }
}
