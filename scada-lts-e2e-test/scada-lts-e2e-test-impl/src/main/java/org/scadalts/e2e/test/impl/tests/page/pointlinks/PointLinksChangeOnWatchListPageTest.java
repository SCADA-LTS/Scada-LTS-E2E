package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@Log4j2
@RunWith(TestParameterizedWithPageRunner.class)
public class PointLinksChangeOnWatchListPageTest {

    @Parameterized.Parameters(name = "{index}: source: {0} - {1}, target: {2} - {3}, pointlink type: {4}, script: {5}")
    public static Object[][] data() {
        String unique = IdentifierObjectFactory.unique();
        return new Object[][] {
                {"ds_test1_" + unique, "dp_test1_source_" + unique, "ds_test1_" + unique, "dp_test1_target_" + unique, EventType.UPDATE, Script.sourceValueIncreasedOne()},
                {"ds_test2_" + unique, "dp_test2_source_" + unique, "ds_test2_" + unique, "dp_test2_target_" + unique, EventType.CHANGE, Script.sourceValueIncreasedOne()},
                {"ds_test3_source_" + unique, "dp_test3_source_" + unique, "ds_test3_target_" + unique, "dp_test3_target_" + unique, EventType.UPDATE, Script.sourceValueIncreasedOne()},
                {"ds_test4_source_" + unique, "dp_test4_source_" + unique, "ds_test4_target_" + unique, "dp_test4_target_" + unique, EventType.CHANGE, Script.sourceValueIncreasedOne()},
        };
    }

    private final DataSourcePointCriteria source;
    private final DataSourcePointCriteria target;
    private final EventType eventType;
    private final Script script;


    public PointLinksChangeOnWatchListPageTest(String dataSoruceSourceName, String dataPointSourceName,
                                               String dataSoruceTargetName, String dataPointTargetName,
                                               EventType eventType, Script script) {
        this.eventType = eventType;

        DataSourceIdentifier dataSourceSourceIdentifier = new DataSourceIdentifier(dataSoruceSourceName, DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceIdentifier dataSourceTargetIdentifier = new DataSourceIdentifier(dataSoruceTargetName, DataSourceType.VIRTUAL_DATA_SOURCE);

        DataPointIdentifier dataPointSourceIdentifier = new DataPointIdentifier(dataPointSourceName, DataPointType.NUMERIC);
        DataPointIdentifier dataPointTargetIdentifier = new DataPointIdentifier(dataPointTargetName, DataPointType.NUMERIC);

        source = DataSourcePointCriteria.criteria(dataSourceSourceIdentifier, dataPointSourceIdentifier);
        target = DataSourcePointCriteria.criteria(dataSourceTargetIdentifier, dataPointTargetIdentifier);

        this.script = script;
    }

    private AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private WatchListPage watchListPageSubject;
    private DataSourcePointIdentifier sourceIdentifier;
    private DataSourcePointIdentifier targetIdentifier;

    @Before
    public void setup() {
        sourceIdentifier = source.getIdentifier();
        targetIdentifier = target.getIdentifier();
        PointLinkCriteria criteria = PointLinkCriteria.criteria(source, target, eventType, script);
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage(),
                criteria);
        watchListPageSubject = allObjectsForPointLinkTestCreator.createObjects();
    }

    @After
    public void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
    }


    @Test
    public void test_point_links() {

        for (String value : ChangePointValuesProvider.paramsToTests()) {

            //given
            String expectedValue = new BigDecimal(value).add(BigDecimal.ONE).toString();

            logger.info("value: {}, value expected: {}", value, expectedValue);

            //when:
            watchListPageSubject.openDataPointValueEditor(sourceIdentifier)
                    .setDataPointValue(sourceIdentifier, value)
                    .confirmDataPointValue(sourceIdentifier)
                    .closeEditorDataPointValue(sourceIdentifier);

            String result = watchListPageSubject.getDataPointValue(targetIdentifier, expectedValue);

            //then:
            assertEquals(expectedValue, result);
        }

    }
}
