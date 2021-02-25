package org.scadalts.e2e.test.impl.tests.service.storungs.live;

import lombok.extern.log4j.Log4j2;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;
import org.scadalts.e2e.test.impl.utils.RegexUtil;
import org.scadalts.e2e.test.impl.utils.TestDataBatch;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@Log4j2
@RunWith(Parameterized.class)
public class GetActiveLivesAfterSeqServiceTest {

    @Parameterized.Parameters(name = "{index}: sequence: {0}")
    public static List<TestDataBatch> data() {
        List<TestDataBatch> result = new ArrayList<>();

        result.addAll(generateDataTestEndValue(1, DataPointNotifierType.ALARM, LoggingType.ALL, 1));
        result.addAll(generateDataTestEndValue(1, DataPointNotifierType.ALARM, LoggingType.ON_CHANGE, 1));
        result.addAll(generateDataTestEndValue(3, DataPointNotifierType.ALARM, LoggingType.ALL, 1));
        result.addAll(generateDataTestEndValue(3, DataPointNotifierType.ALARM, LoggingType.ON_CHANGE, 1));

        result.addAll(generateDataTestEndValue(1, DataPointNotifierType.STORUNG, LoggingType.ALL, 1));
        result.addAll(generateDataTestEndValue(1, DataPointNotifierType.STORUNG, LoggingType.ON_CHANGE, 1));
        result.addAll(generateDataTestEndValue(3, DataPointNotifierType.STORUNG, LoggingType.ALL, 1));
        result.addAll(generateDataTestEndValue(3, DataPointNotifierType.STORUNG, LoggingType.ON_CHANGE, 1));

        return result;
    }

    private final TestDataBatch testDataBatch;

    public GetActiveLivesAfterSeqServiceTest(TestDataBatch testDataBatch) {
        this.testDataBatch = testDataBatch;
    }

    private PaginationParams paginationParams = PaginationParams.all();


    private static DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();
    private static DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private static NavigationPage navigationPage;

    private StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator;

    @BeforeClass
    public static void createDataSource() {
        navigationPage = TestWithPageUtil.openNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria);
        dataSourcePointObjectsCreator.createObjects();
    }

    @Before
    public void setup() {

        DataPointCriteria point = DataPointCriteria.noChange(testDataBatch.getDataPointIdentifier(),
                String.valueOf(testDataBatch.getStartValue()),
                DataPointLoggingProperties.logging(testDataBatch.getLoggingType()));

        storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSourceCriteria, point);
        storungsAndAlarmsObjectsCreator.createObjects();

        List<StorungAlarmResponse> storungAlarmRespons =
                getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                a -> a.size() == testDataBatch.getStartAlarmsNumber(),
                paginationParams);
        String msg = MessageFormat.format(AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED,
                testDataBatch.getStartValue(), testDataBatch.getStartAlarmsNumber(),
                testDataBatch.getDataPointNotifierType().getName());

        assertEquals(msg, testDataBatch.getStartAlarmsNumber(), storungAlarmRespons.size());
    }

    @After
    public void clean() {
        if(storungsAndAlarmsObjectsCreator != null)
            storungsAndAlarmsObjectsCreator.deleteAlaramsAndDataPoints();
    }

    @AfterClass
    public static void cleanAll() {
        if(dataSourcePointObjectsCreator != null)
            dataSourcePointObjectsCreator.deleteObjects();
    }

    @Test
    public void test_when_set_sequence_then_active_live() {

        //when:
        storungsAndAlarmsObjectsCreator.setDataPointValues(testDataBatch.getSequencePointValue());

        //and when:
        List<StorungAlarmResponse> storungAlarmResponse = getAlarmsAndStorungs(testDataBatch.getDataPointIdentifier(), paginationParams);

        //then:
        assertThat(EXPECTED_DATE_ISO, storungAlarmResponse.get(0).getActivationTime(), matchesPattern(RegexUtil.DATE_PSEUDO_ISO_REGEX));
        assertThat(EXPECTED_ACTIVE_ALARM, storungAlarmResponse.get(0).getInactivationTime(), anyOf(is(""), is(" ")));
        assertEquals(testDataBatch.getDataPointIdentifier().getValue(), storungAlarmResponse.get(0).getName());
        assertEquals(testDataBatch.getDataPointNotifierType().getAlarmLevel(), AlarmLevel.getType(storungAlarmResponse.get(0).getLevel()));

    }
}
