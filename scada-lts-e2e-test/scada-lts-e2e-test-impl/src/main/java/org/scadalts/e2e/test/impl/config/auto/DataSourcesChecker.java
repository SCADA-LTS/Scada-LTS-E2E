package org.scadalts.e2e.test.impl.config.auto;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.JsonUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@RunWith(Parameterized.class)
public class DataSourcesChecker {

    @Parameterized.Parameters(name = "number test: {index}, datasource: {0}")
    public static List<DataSourceCriteriaJson> data() {
        return JsonUtil.deserialize("export/datasource/criterias.json");
    }

    private final DataSourceCriteria criteria;

    public DataSourcesChecker(DataSourceCriteriaJson dataSourceCriteriaJson) {
        this.criteria = dataSourceCriteriaJson.toDataSourceSecondCriteria();
    }

    private static DataSourcesPage dataSourcesPage;

    @BeforeClass
    public static void before() {
        NavigationPage navigationPage = TestWithPageUtil.preparingTest();
        dataSourcesPage = navigationPage.openDataSources();
    }

    @Test
    public void check_datasource() {
        assertExists(dataSourcesPage, criteria.getIdentifier());
        if(criteria.isEnabled()) {
            DataSourceIdentifier dataSourceIdentifier = criteria.getIdentifier();
            boolean enabled = dataSourcesPage.isEnabledDataSource(dataSourceIdentifier);
            assertTrue(_genMsg(dataSourceIdentifier), enabled);
        }
    }

    private static String _genMsg(DataSourceIdentifier dataSourceIdentifier) {
        return MessageFormat.format("Expected datasource name: {0}, type: {1} to be enabled.",
                dataSourceIdentifier.getValue(), dataSourceIdentifier.getType());
    }
}
