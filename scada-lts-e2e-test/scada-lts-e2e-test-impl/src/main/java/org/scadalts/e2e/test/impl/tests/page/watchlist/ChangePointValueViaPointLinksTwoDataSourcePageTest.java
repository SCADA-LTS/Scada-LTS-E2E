package org.scadalts.e2e.test.impl.tests.page.watchlist;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.test.impl.runners.E2eTestParameterizedRunner;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.creators.AllObjectsForPointLinkTestCreator;
import org.scadalts.e2e.test.impl.utils.ChangePointValuesProvider;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(E2eTestParameterizedRunner.class)
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
    private static DataSourcePointCriteria source;
    private static DataSourcePointCriteria target;

    @BeforeClass
    public static void setup() {

        source = DataSourcePointCriteria.criteria(IdentifierObjectFactory.dataSourceSourceName(),
                IdentifierObjectFactory.dataPointSourceName());
        target = DataSourcePointCriteria.criteria(IdentifierObjectFactory.dataSourceTargetName(),
                IdentifierObjectFactory.dataPointTargetName());

        PointLinkCriteria criteria = PointLinkCriteria.change(source, target);
        allObjectsForPointLinkTestCreator = new AllObjectsForPointLinkTestCreator(E2eAbstractRunnable.getNavigationPage(),
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
        watchListPageSubject.openDataPointValueEditor(source)
                .setDataPointValue(source, expectedValue)
                .confirmDataPointValue(source)
                .closeEditorDataPointValue(source);

        String result = watchListPageSubject.getDataPointValue(target, expectedValue);

        //then:
        assertEquals(expectedValue, result);
    }
}
