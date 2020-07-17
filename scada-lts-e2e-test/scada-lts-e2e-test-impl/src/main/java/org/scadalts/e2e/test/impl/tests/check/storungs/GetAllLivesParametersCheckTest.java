package org.scadalts.e2e.test.impl.tests.check.storungs;

import lombok.extern.log4j.Log4j2;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.RegexUtil;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.*;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.EXPECTED_DATE_ISO;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.getStorungsAndAlarms;

@Log4j2
@RunWith(TestWithPageRunner.class)
public class GetAllLivesParametersCheckTest {

    private static List<StorungAlarmResponse> storungAlarmResponse;

    @BeforeClass
    public static void setup() {
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
            assertThat("field inactivation-time in: " + res.toString(), res.getInactivationTime(), anyOf(is(""), is(" "), matchesPattern(RegexUtil.DATE_PSEUDO_ISO_REGEX)));
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

            String msg = MessageFormat.format("Failure because: In {0} Alarm Level not any of is: {1}", res, Arrays.asList(AlarmLevel.INFORMATION, AlarmLevel.URGENT));
            assertThat(msg, alarmLevel, anyOf(is(AlarmLevel.INFORMATION), is(AlarmLevel.URGENT)));

            msg = MessageFormat.format("Failure because: {0} {1} wrong Alarm Level.", dataPointNotifierType.getName(), res);
            if (alarm.matcher(res.getName()).find()) {
                assertEquals(msg, AlarmLevel.INFORMATION, alarmLevel);
            }
            if (storung.matcher(res.getName()).find()) {
                assertEquals(msg, AlarmLevel.URGENT, alarmLevel);
            }
        }
    }

    @Test
    public void test_inactivation_time_then_less_24_hours() {

        //given:
        Pattern regex = Pattern.compile(RegexUtil.DATE_PSEUDO_ISO_REGEX);

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse) {
            if(regex.matcher(res.getInactivationTime()).find()) {
                String time = res.getInactivationTime().replace(" ", "T") + "Z";
                Instant inactivationTime = Instant.parse(time);
                Instant nowMinus24h = Instant.now().minus(24, ChronoUnit.HOURS);
                DataPointNotifierType dataPointNotifierType = DataPointNotifierType.getTypeByLevel(AlarmLevel.getType(res.getLevel()));
                String msg = MessageFormat.format("Failure because: inactive {0} {1} earlier than 24h", dataPointNotifierType.getName(), res);
                assertTrue(msg,inactivationTime.getNano() < nowMinus24h.getNano());
            }
        }
    }
}
