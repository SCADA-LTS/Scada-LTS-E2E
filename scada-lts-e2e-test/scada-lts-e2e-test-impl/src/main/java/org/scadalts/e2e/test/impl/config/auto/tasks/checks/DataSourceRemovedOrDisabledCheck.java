package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.JsonUtil;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
public class DataSourceRemovedOrDisabledCheck implements Task {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        List<DataSourceCriteriaJson> criterias = JsonUtil.toList("export/datasource/criterias.json");
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();

        for (DataSourceCriteriaJson criteria : criterias) {
            DataSourceCriteria dataSourceCriteria = criteria.toDataSourceSecondCriteria();
            assertExists(dataSourcesPage, dataSourceCriteria.getIdentifier());
            if(dataSourceCriteria.isEnabled())
                assertTrue(dataSourcesPage.isEnabledDataSource(dataSourceCriteria.getIdentifier()));
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}