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
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.creators.PointLinksObjectsCreator.isUpdate;

@Log4j2
@RunWith(Parameterized.class)
public class PointLinksChangeOnWatchListPageTest {

    @Parameterized.Parameters(name = "{index}: source: {0} - {1}, target: {2} - {3}, pointlink type: {4}")
    public static Object[][] data() {
        String unique = IdentifierObjectFactory.unique();
        return new Object[][] {
                {"ds_test1_" + unique, "dp_test1_source_" + unique, "ds_test1_" + unique, "dp_test1_target_" + unique,
                        EventType.UPDATE},
                {"ds_test2_" + unique, "dp_test2_source_" + unique, "ds_test2_" + unique, "dp_test2_target_" + unique,
                        EventType.CHANGE},
                {"ds_test3_source_" + unique, "dp_test3_source_" + unique, "ds_test3_target_" + unique, "dp_test3_target_" + unique,
                        EventType.UPDATE},
                {"ds_test4_source_" + unique, "dp_test4_source_" + unique, "ds_test4_target_" + unique, "dp_test4_target_" + unique,
                        EventType.CHANGE},
        };
    }

    private final DataSourcePointCriteria source;
    private final DataSourcePointCriteria target;
    private final EventType eventType;


    public PointLinksChangeOnWatchListPageTest(String dataSoruceSourceName, String dataPointSourceName,
                                               String dataSoruceTargetName, String dataPointTargetName,
                                               EventType eventType) {
        this.eventType = eventType;

        DataSourceIdentifier dataSourceSourceIdentifier = new DataSourceIdentifier(dataSoruceSourceName, DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceIdentifier dataSourceTargetIdentifier = new DataSourceIdentifier(dataSoruceTargetName, DataSourceType.VIRTUAL_DATA_SOURCE);

        DataPointIdentifier dataPointSourceIdentifier = new DataPointIdentifier(dataPointSourceName, DataPointType.NUMERIC);
        DataPointIdentifier dataPointTargetIdentifier = new DataPointIdentifier(dataPointTargetName, DataPointType.NUMERIC);

        source = DataSourcePointCriteria.criteria(dataSourceSourceIdentifier, dataPointSourceIdentifier);
        target = DataSourcePointCriteria.criteria(dataSourceTargetIdentifier, dataPointTargetIdentifier);
    }

    private AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private WatchListPage watchListPageSubject;
    private DataSourcePointIdentifier sourceIdentifier;
    private DataSourcePointIdentifier targetIdentifier;
    private PointLinksPage pointLinksPage;
    private PointLinkCriteria criteria;
    private WatchListIdentifier watchListIdentifier;

    @Before
    public void setup() {
        sourceIdentifier = source.getIdentifier();
        targetIdentifier = target.getIdentifier();
        criteria = PointLinkCriteria.criteria(source, target, eventType, Script.empty());
        watchListIdentifier = IdentifierObjectFactory.watchListName();
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.openNavigationPage(),
                criteria, watchListIdentifier);
        watchListPageSubject = allObjectsForPointLinkTestCreator.createObjects();
        pointLinksPage = allObjectsForPointLinkTestCreator.openPage();
    }

    @After
    public void clean() {
        if(allObjectsForPointLinkTestCreator != null)
            allObjectsForPointLinkTestCreator.deleteObjects();
    }


    @Test
    public void test_point_links() {
        List<String> values = new ArrayList<>(ChangePointValuesProvider.paramsToTests());
        String previousValue = "";
        String expectedValue = "";

        for(int i = 0 ; i < values.size() ; i++) {

            //given:
            Script script = Script.sourceValueIncreased(i);
            String value = values.get(i);

            pointLinksPage.reopen()
                    .openPointLinkEditor(criteria)
                    .setScript(script)
                    .savePointLink();

            expectedValue = isUpdate(eventType, previousValue, value) ? script.executeInJava(value) : expectedValue;
            logger.info("value: {}, expected: {}, script: {}", value, expectedValue, script);

            //when:
            watchListPageSubject.reopen()
                    .selectWatchList(watchListIdentifier)
                    .openDataPointValueEditor(sourceIdentifier)
                    .setDataPointValue(sourceIdentifier, value)
                    .confirmDataPointValue(sourceIdentifier)
                    .closeEditorDataPointValue(sourceIdentifier);

            String result = watchListPageSubject.getDataPointValue(targetIdentifier, expectedValue);

            //then:
            assertEquals(expectedValue, result);

            previousValue = value;
        }

    }
}
