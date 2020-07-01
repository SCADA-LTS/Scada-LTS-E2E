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
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.RegexUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class GetActiveLivesServiceTest {

    private static DataPointIdentifier alarmIdentifier;
    private static DataPointIdentifier storungIdentifier;
    private static PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private static StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;

    @BeforeClass
    public static void setup() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        alarmIdentifier = IdentifierObjectFactory.dataPointAlarmBinaryTypeName();
        storungIdentifier = IdentifierObjectFactory.dataPointStorungBinaryTypeName();

        DataPointCriteria pointAlarm = DataPointCriteria.noChange(alarmIdentifier, "0");
        DataPointCriteria pointStorung = DataPointCriteria.noChange(storungIdentifier, "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();

        storungsAndAlarmsObjectsCreator =
                new StorungsAndAlarmsObjectsCreator(navigationPage, dataSourceCriteria, pointAlarm, pointStorung);
        storungsAndAlarmsObjectsCreator.createObjects();

        //Simulate the change of value on the points 0 -> 1
        storungsAndAlarmsObjectsCreator.setDataPointValue("1");

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(alarmIdentifier, paginationParams);

        //then:
        String msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                "0 -> 1", "alarms", "1");
        assertEquals(msg,1, storungAlarmResponse.size());

        //when:
        storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(storungIdentifier, paginationParams);

        //then:
        msg = MessageFormat.format(AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z,
                "0 -> 1", "storungs", "1");
        assertEquals(msg,1, storungAlarmResponse.size());

    }

    @AfterClass
    public static void clean() {
        storungsAndAlarmsObjectsCreator.deleteObjects();
    }

    @Test
    public void test_response_activation_time_then_is_date_iso_for_alarm() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(alarmIdentifier, paginationParams);

        //then:
        assertThat(EXPECTED_DATE_ISO, storungAlarmResponse.get(0).getActivationTime(), matchesPattern(RegexUtil.DATE_ISO_REGEX));
    }

    @Test
    public void test_response_inactivation_time_then_empty_for_alarm() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(alarmIdentifier, paginationParams);

        //then:
        assertThat(EXPECTED_ACTIVE_ALARM, storungAlarmResponse.get(0).getInactivationTime(), anyOf(is(""), is(" ")));
    }

    @Test
    public void test_response_name_then_point_name_for_alarm() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(alarmIdentifier, paginationParams);

        //then:
        assertEquals(alarmIdentifier.getValue(), storungAlarmResponse.get(0).getName());
    }

    @Test
    public void test_response_level_then_AlarmLevel_for_alarm() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(alarmIdentifier, paginationParams);

        //then:
        assertEquals(AlarmLevel.INFORMATION.getId(), storungAlarmResponse.get(0).getLevel());
    }

    @Test
    public void test_response_activation_time_then_is_date_iso_for_storung() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(storungIdentifier, paginationParams);

        //then:
        assertThat(EXPECTED_DATE_ISO, storungAlarmResponse.get(0).getActivationTime(), matchesPattern(RegexUtil.DATE_ISO_REGEX));
    }

    @Test
    public void test_response_inactivation_time_then_empty_for_storung() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(storungIdentifier, paginationParams);

        //then:
        assertThat(EXPECTED_ACTIVE_ALARM, storungAlarmResponse.get(0).getInactivationTime(), anyOf(is(""), is(" ")));
    }

    @Test
    public void test_response_name_then_point_name_for_storung() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(storungIdentifier, paginationParams);

        //then:
        assertEquals(storungIdentifier.getValue(), storungAlarmResponse.get(0).getName());
    }

    @Test
    public void test_response_level_then_AlarmLevel_for_storung() {

        //when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungsSortByActivationTime(storungIdentifier, paginationParams);

        //then:
        assertEquals(AlarmLevel.URGENT.getId(), storungAlarmResponse.get(0).getLevel());
    }
}