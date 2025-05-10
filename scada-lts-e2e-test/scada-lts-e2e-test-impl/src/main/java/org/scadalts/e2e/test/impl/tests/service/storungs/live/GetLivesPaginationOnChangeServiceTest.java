package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(Parameterized.class)
public class GetLivesPaginationOnChangeServiceTest {

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

    public GetLivesPaginationOnChangeServiceTest(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    private static StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;
    private static List<StorungAlarmResponse> getResult10;

    @BeforeClass
    public static void setup() {

        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond();

        DataPointIdentifier[] idnetifiers = new DataPointIdentifier[] {

                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),
                IdentifierObjectFactory.dataPointAlarmBinaryTypeName(),

                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
                IdentifierObjectFactory.dataPointStorungBinaryTypeName(),
        };

        VirtualDataPointCriteria[] points = Stream.of(idnetifiers)
                .map(a -> VirtualDataPointCriteria.noChange(a, "0",
                        DataPointLoggingProperties.logging(LoggingType.ON_CHANGE)))
                .toArray(a -> new VirtualDataPointCriteria[10]);

        NavigationPage navigationPage = TestWithPageUtil.openNavigationPage();

        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSourceCriteria, points);
        storungsAndAlarmsObjectsCreator.createObjects();
        storungsAndAlarmsObjectsCreator.setDataPointValue("1");


        PaginationParams pagination10 = PaginationParams.builder()
                .limit(10)
                .offset(0)
                .build();

        sleep();

        //when:
        getResult10 = getStorungsAndAlarms(pagination10);

        //then:
        String msg = MessageFormat.format(EXPECTED_X_ALARMS_STORUNGS, "10");
        assertNotNull(getResult10);
        assertEquals(msg, 10, getResult10.size());
    }

    @AfterClass
    public static void clean() {
        if(storungsAndAlarmsObjectsCreator != null)
            storungsAndAlarmsObjectsCreator.deleteObjects();
    }

    @Test
    public void test_get_live_alarms_limit() {

        //given:
        PaginationParams paginationParams = PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build();

        //when:
        List<StorungAlarmResponse> getResult = getStorungsAndAlarms(paginationParams);

        //then:
        assertNotNull(getResult);
        assertEquals(limit, getResult.size());
    }

    @Test
    public void test_get_live_alarms_offset() {

        //given:
        PaginationParams paginationToTest = PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build();

        //when:
        List<StorungAlarmResponse> getResult = getStorungsAndAlarms(paginationToTest);

        //then:
        assertNotNull(getResult);
        assertEquals(getResult10.subList(offset, limit + offset), getResult);
    }

    @Test
    public void test_get_live_alarms_offset2() {

        //given:
        PaginationParams paginationToTest = PaginationParams.builder()
                .limit(limit)
                .offset(offset)
                .build();
        int max = 10 - offset;
        int number = (max < limit ? max : limit);

        //when:
        List<StorungAlarmResponse> getResult = getStorungsAndAlarms(paginationToTest);

        //then:
        assertNotNull(getResult);
        String msg = MessageFormat.format(EXPECTED_LARGER_OR_EQUALS_TO_X_ALARMS_STORUNGS_BUT_WAS_Y,
                number, getResult.size());

        assertTrue(msg, number <= getResult.size());
    }
}
