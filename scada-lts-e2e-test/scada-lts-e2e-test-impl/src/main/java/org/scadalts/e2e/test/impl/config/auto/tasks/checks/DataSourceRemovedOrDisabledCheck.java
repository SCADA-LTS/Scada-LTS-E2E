package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.JsonUtil;

import java.text.MessageFormat;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
@Log4j2
public class DataSourceRemovedOrDisabledCheck implements Task {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        List<DataSourceCriteriaJson> criterias = JsonUtil.deserialize("export/datasource/criterias.json");
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();

        for (DataSourceCriteriaJson criteria : criterias) {
            DataSourceCriteria dataSourceCriteria = criteria.toDataSourceSecondCriteria();
            assertExists(dataSourcesPage, dataSourceCriteria.getIdentifier());
            if(dataSourceCriteria.isEnabled()) {
                DataSourceIdentifier dataSourceIdentifier = dataSourceCriteria.getIdentifier();
                boolean enabled = dataSourcesPage.isEnabledDataSource(dataSourceIdentifier);
                assertTrue(_genMsg(dataSourceIdentifier), enabled);
            }
        }
    }

    private static String _genMsg(DataSourceIdentifier dataSourceIdentifier) {
        return MessageFormat.format("Expected datasource name: {0}, type: {1} to be enabled.",
                dataSourceIdentifier.getValue(), dataSourceIdentifier.getType());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}