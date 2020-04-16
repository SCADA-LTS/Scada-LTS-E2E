package org.scadalts.e2e.test.impl.config.auto.tasks.imports;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.json.DataSourceCriteriaJson;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.JsonUtil;

import java.util.List;

@Data
public class ImportDataSourcesWithEnabledTask implements Task {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        List<DataSourceCriteriaJson> criterias = JsonUtil.toList("export/datasource/criterias.json");
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();

        for (DataSourceCriteriaJson criteria : criterias) {
            DataSourceCriteria dataSourceCriteria = criteria.toDataSourceSecondCriteria();
            if(criteria.isEnabled() && !dataSourcesPage.isEnabledDataSource(dataSourceCriteria.getIdentifier()))
                dataSourcesPage.enableDataSource(dataSourceCriteria.getIdentifier());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}