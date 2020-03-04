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

import java.util.Set;

import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
public class ConfigEventDetectorSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<EventDetectorCriteria> eventDetectorCriterias;

    @Override
    public void check() {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        for (EventDetectorCriteria eventDetectorCriteria : eventDetectorCriterias) {
            DataSourceCriteria dataSourceCriteria = eventDetectorCriteria.getDataSourcePointCriteria().getDataSource();
            DataPointCriteria dataPointCriteria = eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint();

            EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.openDataSourceEditor(dataSourceCriteria);
            PropertiesDataPointPage propertiesDataPointPage = editDataSourceWithPointListPage.openDataPointProperties(dataPointCriteria);
            propertiesDataPointPage.waitOnEventDetectorTable();
            assertExists(propertiesDataPointPage, eventDetectorCriteria);
        }
    }
}
