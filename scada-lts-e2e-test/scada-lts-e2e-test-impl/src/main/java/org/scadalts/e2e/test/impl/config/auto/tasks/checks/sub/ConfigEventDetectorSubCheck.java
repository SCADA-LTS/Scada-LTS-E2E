package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.PropertiesDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
public class ConfigEventDetectorSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull EventDetectorCriteria eventDetectorCriteria;

    @Override
    public void execute() {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        DataSourceCriteria dataSourceCriteria = eventDetectorCriteria.getDataSourcePointCriteria().getDataSource();
        DataPointCriteria dataPointCriteria = eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint();

        EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.openDataSourceEditor(dataSourceCriteria);
        PropertiesDataPointPage propertiesDataPointPage = editDataSourceWithPointListPage.openDataPointProperties(dataPointCriteria);
        propertiesDataPointPage.waitOnEventDetectorTable();
        assertExists(propertiesDataPointPage,eventDetectorCriteria);
    }
}
