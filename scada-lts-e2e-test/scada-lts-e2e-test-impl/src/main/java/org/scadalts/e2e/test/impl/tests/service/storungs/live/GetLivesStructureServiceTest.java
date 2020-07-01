package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class GetLivesStructureServiceTest {

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

    public GetLivesStructureServiceTest(int offset, int limit) {
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

        DataPointCriteria[] activeNotifierAlarams = Stream.of(activeIdnetifiers)
                .map(a -> DataPointCriteria.noChange(a, "1"))
                .toArray(a -> new DataPointCriteria[activeIdnetifiers.length]);

        DataPointCriteria[] inactiveNotifierAlarams = Stream.of(inactiveIdnetifiers)
                .map(a -> DataPointCriteria.noChange(a, "1"))
                .toArray(a -> new DataPointCriteria[inactiveIdnetifiers.length]);


        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        activePoints = new StorungsAndAlarmsObjectsCreator(navigationPage, activeNotifierAlarams);
        activePoints.createObjects();

        inactivePoints = new StorungsAndAlarmsObjectsCreator(navigationPage, inactiveNotifierAlarams);
        inactivePoints.createObjects();
        inactivePoints.setDataPointValue(0);

        StorungsAndAlarmsUtil.getAlarmsAndStorungs(PaginationParams.builder()
                .offset(0)
                .limit(9999)
                .build(), 12);

        StorungsAndAlarmsUtil.getActiveAlarmsAndStorungs(PaginationParams.builder()
                .offset(0)
                .limit(9999)
                .build(), 6);

        StorungsAndAlarmsUtil.getInactiveAlarmsAndStorungs(PaginationParams.builder()
                .offset(0)
                .limit(9999)
                .build(), 6);
    }

    @AfterClass
    public static void clean() {

        activePoints.deleteObjects();
        inactivePoints.deleteObjects();
    }

    @Test
    public void test_sort_structure_active_lives() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getActiveAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<StorungAlarmResponse> sorted = StorungsAndAlarmsUtil.sortByActivationTime(responses);

        //then:
        assertEquals(sorted, responses);
    }

    @Test
    public void test_sort_structure_inactive_lives() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getInactiveAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<StorungAlarmResponse> sorted = StorungsAndAlarmsUtil.sortByActivationTime(responses);

        //then:
        assertEquals(sorted, responses);
    }

    @Test
    public void test_ref_structure_live() {

        //when:
        List<StorungAlarmResponse> responses = StorungsAndAlarmsUtil.getAlarmsAndStorungs(PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build());

        List<StorungAlarmResponse> ref = StorungsAndAlarmsUtil.getReferenceStructure(responses);

        //then:
        assertEquals(ref, responses);
    }

}
