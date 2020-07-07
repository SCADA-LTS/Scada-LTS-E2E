package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.utils.RegexUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.assertThat;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.EXPECTED_DATE_ISO;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.getStorungsAndAlarms;

@RunWith(TestWithPageRunner.class)
public class GetAllLivesParametersServiceTest {

    private static PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private static StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;
    private static List<StorungAlarmResponse> storungAlarmResponse;

    @BeforeClass
    public static void setup() {

        DataPointCriteria[] points = {
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "0"),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "0"),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.ALARM), "0"),
                DataPointCriteria.noChange(IdentifierObjectFactory.dataPointNotifierBinaryTypeName(DataPointNotifierType.STORUNG), "0"),
        };

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, points);
        storungsAndAlarmsObjectsCreator.createObjects();
        storungsAndAlarmsObjectsCreator.setDataPointValue(1)
                .setDataPointValue(points[1], 0)
                .setDataPointValue(points[3], 0);

        storungAlarmResponse = getStorungsAndAlarms(paginationParams);

    }

    @AfterClass
    public static void clean() {
        storungsAndAlarmsObjectsCreator.deleteObjects();
    }


    @Test
    public void test_response_activation_time_then_is_date_iso() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat(EXPECTED_DATE_ISO + " in:" + res.toString(), res.getActivationTime(), matchesPattern(RegexUtil.DATE_ISO_REGEX));
    }

    @Test
    public void test_response_inactivation_time_then_empty() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat("field inactivation-time in: " + res.toString(), res.getInactivationTime(), anyOf(is(""), is(" "), matchesPattern(RegexUtil.DATE_ISO_REGEX)));
    }

    @Test
    public void test_response_name_then_point_name() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat("field name in: " + res.toString(), res.getName(), matchesPattern(RegexUtil.DATA_POINT_NOTIFIER_NAME_REGEX));
    }

    @Test
    public void test_response_level_then_AlarmLevel() {

        //then:
        for (StorungAlarmResponse res : storungAlarmResponse)
            assertThat("field level in: " + res.toString(), res.getLevel(), anyOf(is("1"), is("2")));
    }
}
