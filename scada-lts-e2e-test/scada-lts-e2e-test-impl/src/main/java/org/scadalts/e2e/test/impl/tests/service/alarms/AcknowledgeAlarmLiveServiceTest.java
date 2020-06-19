package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory.unique;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.getAlarms;
import static org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil.acknowledgeAlarm;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class AcknowledgeAlarmLiveServiceTest {

    @Parameterized.Parameters(name = "{index}: data point: {0}")
    public static Object[] data() {
        return new Object[] {
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
        };
    }

    private final DataPointIdentifier identifier;

    public AcknowledgeAlarmLiveServiceTest(DataPointIdentifier identifier) {
        this.identifier = identifier;
    }

    private PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private WatchListObjectsCreator watchListObjectsCreator;
    private WatchListPage watchListPage;

    private DataSourcePointIdentifier dataSourcePointIdentifier;
    private DataPointIdentifier uniqueIdentifier;

    @Before
    public void setup() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        uniqueIdentifier = identifier.newIdentifier(unique());
        DataPointCriteria point = DataPointCriteria.noChange(uniqueIdentifier, "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, point);
        dataSourcePointObjectsCreator.createObjects();

        dataSourcePointIdentifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                uniqueIdentifier);

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(dataSourcePointIdentifier));
        watchListObjectsCreator.createObjects();

        watchListPage = navigationPage.openWatchList();

    }

    @After
    public void clean() {
        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }


    @Test
    public void test_acknowledge_for_inactive_alarm_then_removed_from_live() {

        //given:
        watchListPage.setValue(dataSourcePointIdentifier, "1")
                .setValue(dataSourcePointIdentifier, "0");

        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(1, alarmResponses.size());

        //when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(200, getResponse.getStatus());
        }

        //then:
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(0, alarmResponses.size());

    }

    @Test
    public void test_acknowledge_for_active_alarm_then_not_removed_from_live() {

        //given:
        watchListPage.setValue(dataSourcePointIdentifier, "1")
                .setValue(dataSourcePointIdentifier, "0")
                .setValue(dataSourcePointIdentifier, "1");

        List<AlarmResponse> alarmResponses = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(2, alarmResponses.size());

        //when:
        for(AlarmResponse alarmResponse : alarmResponses) {

            E2eResponse<String> getResponse = acknowledgeAlarm(alarmResponse.getId(),
                    TestImplConfiguration.waitingAfterSetPointValueMs);

            assertEquals(200, getResponse.getStatus());
        }

        //then:
        alarmResponses = getAlarms(uniqueIdentifier, paginationParams);
        assertEquals(1, alarmResponses.size());

    }
}
