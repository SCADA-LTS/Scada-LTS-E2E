package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(Parameterized.class)
public class GetLivesGroupingSortOnChangeServiceTest {

    @Parameterized.Parameters(name = "{index}: offset: {0}, limit: {1}")
    public static Object[][] data() {
        return new Object[][] {
                {0, 12}, {1, 11}, {2, 10}, {3, 9}, {4, 8}, {5, 7}, {6, 6}, {7, 5},
                {8, 4}, {9, 3}, {10, 4}, {11, 1}, {12, 0}, {8, 2}, {9, 1}, {10, 0},
                {0, 2}, {2, 2}, {4, 2}, {6, 2}, {8, 2}, {10, 2}, {0, 3}, {3, 3},
                {6, 3}, {9, 3}, {0, 4}, {4, 4}, {8, 4}, {0, 5}, {5, 5}, {10, 2},
                {0, 6}, {6, 6}, {0, 7}, {7, 5}, {0, 8}, {8, 4}, {0, 9999}
        };
    }

    private final int offset;
    private final int limit;

    public GetLivesGroupingSortOnChangeServiceTest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private static StorungsAndAlarmsObjectsCreator activePoints;
    private static StorungsAndAlarmsObjectsCreator inactivePoints;

    @BeforeClass
    public static void setup() {

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

        VirtualDataPointCriteria[] activeNotifierAlarams = Stream.of(activeIdnetifiers)
                .map(a -> VirtualDataPointCriteria.noChange(a, "1",
                        DataPointLoggingProperties.logging(LoggingType.ON_CHANGE)))
                .toArray(a -> new VirtualDataPointCriteria[activeIdnetifiers.length]);

        VirtualDataPointCriteria[] inactiveNotifierAlarams = Stream.of(inactiveIdnetifiers)
                .map(a -> VirtualDataPointCriteria.noChange(a, "1",
                        DataPointLoggingProperties.logging(LoggingType.ON_CHANGE)))
                .toArray(a -> new VirtualDataPointCriteria[inactiveIdnetifiers.length]);


        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();
        activePoints = new StorungsAndAlarmsObjectsCreator(navigationPage, activeNotifierAlarams);
        activePoints.createObjects();

        inactivePoints = new StorungsAndAlarmsObjectsCreator(navigationPage, inactiveNotifierAlarams);
        inactivePoints.createObjects();
        inactivePoints.setDataPointValue(0);

        PaginationParams paginationParams = PaginationParams.all();

        sleep();

        StorungsAndAlarmsUtil.getStorungsAndAlarms(paginationParams, 12);
        StorungsAndAlarmsUtil.getActiveAlarmsAndStorungs(paginationParams, 6);
        StorungsAndAlarmsUtil.getInactiveAlarmsAndStorungs(paginationParams, 6);

    }

    @AfterClass
    public static void clean() {
        sleep();
        if(activePoints != null)
            activePoints.deleteObjects();
        if(inactivePoints != null)
            inactivePoints.deleteObjects();
    }

    @Test
    public void test_sort_structure_active_lives() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getActiveAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<StorungAlarmResponse> sorted = StorungsAndAlarmsUtil.sortByActivationTimeDesc(responses);

        //then:
        assertEquals(EXPECTED_ALARMS_STORUNGS_SORTED_DESCENDING_BY_ACTIVATION_TIME, sorted, responses);
    }

    @Test
    public void test_sort_structure_inactive_lives() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getInactiveAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<StorungAlarmResponse> sorted = StorungsAndAlarmsUtil.sortByInactivationTimeDesc(responses);

        //then:
        assertEquals(EXPECTED_ALARMS_STORUNGS_SORTED_DESCENDING_BY_INACTIVATION_TIME, sorted, responses);
    }

    @Test
    public void test_grouping_structure_lives() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getStorungsAndAlarms(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<StorungAlarmResponse> ref = StorungsAndAlarmsUtil.getReferenceStructure(responses);

        //then:
        assertEquals(EXPECTED_ACTIVE_ABOVE_BELOW_THEM_INACTIVE_LIVES_AND_SORTED_DESC, ref, responses);
    }

}
