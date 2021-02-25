package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.utils.RegexUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

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
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.sleep;

@Log4j2
public class GetAllLivesParametersServiceTest {

    private static PaginationParams paginationParams = PaginationParams.all();

    private static StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;
    private static List<StorungAlarmResponse> storungAlarmResponse;

    @BeforeClass
    public static void setup() {

        DataPointCriteria[] points = {
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "0", DataPointLoggingProperties.change()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "0", DataPointLoggingProperties.change()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "1", DataPointLoggingProperties.change()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "1", DataPointLoggingProperties.change()),

                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "0", DataPointLoggingProperties.allData()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "0", DataPointLoggingProperties.allData()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "1", DataPointLoggingProperties.allData()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "1", DataPointLoggingProperties.allData()),

                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "0", DataPointLoggingProperties.tsChange()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "0", DataPointLoggingProperties.tsChange()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "1", DataPointLoggingProperties.tsChange()),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "1", DataPointLoggingProperties.tsChange()),
        };

        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, points);
        storungsAndAlarmsObjectsCreator.createObjects();
        storungsAndAlarmsObjectsCreator.setDataPointValue(1)
                .setDataPointValue(points[1], 0)
                .setDataPointValue(points[3], 0);

        storungAlarmResponse = getStorungsAndAlarms(paginationParams);
        logger.info("size lives: {}", storungAlarmResponse.size());
    }

    @AfterClass
    public static void clean() {
        if(storungsAndAlarmsObjectsCreator != null)
            storungsAndAlarmsObjectsCreator.deleteObjects();
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
        Pattern regex = Pattern.compile(RegexUtil.DATE_PSEUDO_ISO_REGEX);

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse) {
            if(regex.matcher(res.getInactivationTime()).find()) {
                String time = RegexUtil.getDateIso(res.getInactivationTime());
                Instant inactivationTime = Instant.parse(time);
                Instant nowMinus24h = Instant.now().minus(24, ChronoUnit.HOURS);
                DataPointNotifierType dataPointNotifierType = DataPointNotifierType.getTypeByLevel(res.getLevel());
                String msg = MessageFormat.format("Failure because: inactive {0} {1} older than 24h", dataPointNotifierType.getName(), res);
                assertTrue(msg,inactivationTime.compareTo(nowMinus24h) > -1);
            }
        }
    }
}
