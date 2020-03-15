package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(TestParameterizedWithPageRunner.class)
public class ChangePointValueViaPointLinksTwoDataSourcePageTest {

    @Parameterized.Parameters(name = "{index}: value:{0}")
    public static Collection<String> data() {
        return ChangePointValuesProvider.paramsToTests();
    }

    private final String expectedValue;

    public ChangePointValueViaPointLinksTwoDataSourcePageTest(String expectedValue) {
        this.expectedValue = expectedValue;
    }

    private static AllObjectsForPointLinkTestCreator allObjectsForPointLinkTestCreator;
    private static WatchListPage watchListPageSubject;
    private static DataSourcePointIdentifier sourceIdentifier;
    private static DataSourcePointIdentifier targetIdentifier;

    @BeforeClass
    public static void setup() {

        DataSourcePointCriteria source = DataSourcePointCriteria.criteria(IdentifierObjectFactory.dataSourceSourceName(DataSourceType.VIRTUAL_DATA_SOURCE),
                IdentifierObjectFactory.dataPointSourceName(DataPointType.NUMERIC));
        DataSourcePointCriteria target = DataSourcePointCriteria.criteria(IdentifierObjectFactory.dataSourceTargetName(DataSourceType.VIRTUAL_DATA_SOURCE),
                IdentifierObjectFactory.dataPointTargetName(DataPointType.NUMERIC));

        sourceIdentifier = source.getIdentifier();
        targetIdentifier = target.getIdentifier();

        PointLinkCriteria criteria = PointLinkCriteria.change(source, target);
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(TestWithPageUtil.getNavigationPage(),
                criteria);
        watchListPageSubject = allObjectsForPointLinkTestCreator.createObjects();
    }

    @AfterClass
    public static void clean() {
        allObjectsForPointLinkTestCreator.deleteObjects();
    }

    @Test
    public void test_point_links() {

        //when:
        watchListPageSubject.openDataPointValueEditor(sourceIdentifier)
                .setDataPointValue(sourceIdentifier, expectedValue)
                .confirmDataPointValue(sourceIdentifier)
                .closeEditorDataPointValue(sourceIdentifier);

        String result = watchListPageSubject.getDataPointValue(targetIdentifier, expectedValue);

        //then:
        assertEquals(expectedValue, result);
    }
}
