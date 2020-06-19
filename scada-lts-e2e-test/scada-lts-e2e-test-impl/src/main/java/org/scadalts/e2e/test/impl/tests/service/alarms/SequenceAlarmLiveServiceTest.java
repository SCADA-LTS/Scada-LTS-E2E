package org.scadalts.e2e.test.impl.tests.service.alarms;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.PermutationTestData;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.generateDataTest;
import static org.scadalts.e2e.test.impl.utils.AlarmsAndStorungsUtil.getAlarms;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class SequenceAlarmLiveServiceTest {

    @Parameterized.Parameters(name = "{index}: sequence: {0}")
    public static List<PermutationTestData> data() {
        List<PermutationTestData> result = generateDataTest(4, IdentifierObjectFactory.dataPointAlarmBinaryTypeName());
        result.addAll(generateDataTest(4, IdentifierObjectFactory.dataPointStorungBinaryTypeName()));
        return result;
    }

    private final PermutationTestData permutationData;

    public SequenceAlarmLiveServiceTest(PermutationTestData permutationData) {
        this.permutationData = permutationData;
    }

    private PaginationParams paginationParams = PaginationParams.builder()
            .limit(9999)
            .offset(0)
            .build();

    private DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private WatchListObjectsCreator watchListObjectsCreator;
    private WatchListPage watchListPage;

    private DataSourcePointIdentifier dataSourcePointIdentifier;

    @Before
    public void setup() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond();

        DataPointCriteria point = DataPointCriteria.noChange(permutationData.getDataPointIdentifier(), "0");

        NavigationPage navigationPage = TestWithPageUtil.getNavigationPage();
        dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, point);
        dataSourcePointObjectsCreator.createObjects();

        dataSourcePointIdentifier = new DataSourcePointIdentifier(dataSourceCriteria.getIdentifier(),
                permutationData.getDataPointIdentifier());

        watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, WatchListCriteria.criteria(dataSourcePointIdentifier));
        watchListObjectsCreator.createObjects();

        watchListPage = navigationPage.openWatchList();

    }

    @After
    public void clean() {
        watchListObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
    }


    @Test
    public void test_sequence() {

        //when:
        List<AlarmResponse> alarmResponses = getAlarms(permutationData.getDataPointIdentifier(), paginationParams);

        //then:
        assertEquals(0, alarmResponses.size());

        //and when:
        watchListPage.setSequenceInts(dataSourcePointIdentifier, permutationData.getPermutationData().getPermutations());

        //then:
        alarmResponses = getAlarms(permutationData.getDataPointIdentifier(), paginationParams);
        assertEquals(permutationData.getNumberRisingSlopes(), alarmResponses.size());

    }
}
