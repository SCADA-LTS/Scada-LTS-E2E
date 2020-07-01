package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class StartInactiveLivesServiceTest {

    private static DataPointIdentifier alarmIdentifier;
    private static DataPointIdentifier stroungIdentifier;
    private static PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private static StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;

    @BeforeClass
    public static void setup() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        alarmIdentifier = IdentifierObjectFactory.dataPointAlarmBinaryTypeName();
        stroungIdentifier = IdentifierObjectFactory.dataPointStorungBinaryTypeName();

        DataPointCriteria pointAlarm = DataPointCriteria.noChange(alarmIdentifier, "0");
        DataPointCriteria pointStorung = DataPointCriteria.noChange(stroungIdentifier, "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSourceCriteria, pointAlarm, pointStorung);
        storungsAndAlarmsObjectsCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        storungsAndAlarmsObjectsCreator.deleteObjects();
    }

    @Test
    public void test_start_value_zero_for_alarm_then_not_raise_alarm() {
        
        //when:
        List<StorungAlarmResponse> storungAlarmResponse = StorungsAndAlarmsUtil.getAlarmsSortByActivationTime(alarmIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z, "0", "alarm", "0");
        assertEquals(msg,0, storungAlarmResponse.size());
    }

    @Test
    public void test_start_value_zero_for_storung_then_not_raise_storung() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = StorungsAndAlarmsUtil.getAlarmsSortByActivationTime(stroungIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z, "0", "storung", "0");
        assertEquals(msg,0, storungAlarmResponse.size());
    }
}