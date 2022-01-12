package org.scadalts.e2e.test.impl.tests.check.storungs;

import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.utils.RegexUtil;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.*;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
public class GetAllLivesParametersCheckTest {

    private static List<StorungAlarmResponse> storungAlarmResponse;

    @BeforeClass
    public static void setup() {
        TestWithoutPageUtil.preparingTest();
        PaginationParams paginationParams = PaginationParams.all();
        storungAlarmResponse = getStorungsAndAlarms(paginationParams);
        logger.info("size lives: {}", storungAlarmResponse.size());
    }

    @Test
    public void test_response_activation_time_then_is_date_iso() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat(EXPECTED_DATE_ISO + " in:" + res.toString(), res.getActivationTime(), matchesPattern(RegexUtil.DATE_PSEUDO_ISO_REGEX));
    }

    @Test
    public void test_response_inactivation_time_then_empty() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat("field inactivation-time in: " + res.toString(), res.getInactivationTime(),
                    anyOf(is(""), is(" "), is("1970-01-01 01:00:00"),
                    matchesPattern(RegexUtil.DATE_PSEUDO_ISO_REGEX)));
    }

    @Test
    public void test_response_name_then_point_name() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat("field name in: " + res.toString(), res.getName(), matchesPattern(RegexUtil.DATA_POINT_NOTIFIER_NAME_REGEX));
    }

    @Test
    public void test_response_level_then_AlarmLevel() {

        //given:
        Pattern alarm = Pattern.compile(RegexUtil.DATA_POINT_ALARM_NOTIFIER_NAME_REGEX);
        Pattern storung = Pattern.compile(RegexUtil.DATA_POINT_STORUNG_NOTIFIER_NAME_REGEX);

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse) {
            AlarmLevel alarmLevel = AlarmLevel.getType(res.getLevel());
            DataPointNotifierType dataPointNotifierType = DataPointNotifierType.getTypeByLevel(alarmLevel);

            AlarmLevel alarmLevelUrgent = AlarmLevel.URGENT;
            AlarmLevel alarmLevelInformation = AlarmLevel.INFORMATION;
            String msg = MessageFormat.format("Failure because: In {0} Alarm Level not any of is: {1}", res, Arrays.asList(alarmLevelUrgent, alarmLevelInformation));
            assertThat(msg, alarmLevel, anyOf(is(alarmLevelUrgent), is(alarmLevelInformation)));

            msg = MessageFormat.format("Failure because: {0} {1} wrong Alarm Level.", dataPointNotifierType.getName(), res);
            if (alarm.matcher(res.getName()).find()) {
                assertEquals(msg, alarmLevelUrgent, alarmLevel);
            }
            if (storung.matcher(res.getName()).find()) {
                assertEquals(msg, alarmLevelInformation, alarmLevel);
            }
        }
    }

    @Test
    public void test_inactivation_time_then_less_24_hours() {

        //given:
        List<StorungAlarmResponse> responses = new ArrayList<>();

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse) {

            if(!isActive(res)) {
                assertThat(EXPECTED_DATE_ISO + " in:" + res.toString(), res.getInactivationTime(), matchesPattern(RegexUtil.DATE_PSEUDO_ISO_REGEX));
                String time = RegexUtil.getDateIso(res.getInactivationTime());
                Instant inactivationTime = Instant.parse(time);
                Instant nowMinus24h = Instant.now().minus(24, ChronoUnit.HOURS);
                if(inactivationTime.compareTo(nowMinus24h) < 0)
                    responses.add(res);
            }
        }

        //then:
        String msg = MessageFormat.format("Failure because: inactive older than 24h : {0}", responses.stream()
                .map(StorungAlarmResponse::toString)
                .collect(Collectors.joining("\n")));
        assertTrue(msg, responses.isEmpty());
    }

    @Test
    public void test_grouping_structure_lives() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getStorungsAndAlarms(PaginationParams.builder()
                .limit(9999)
                .offset(0)
                .build());

        List<StorungAlarmResponse> ref = StorungsAndAlarmsUtil.getReferenceStructure(responses);

        //then:
        assertEquals(EXPECTED_ACTIVE_ABOVE_BELOW_THEM_INACTIVE_LIVES_AND_SORTED_DESC, ref, responses);
    }
}
