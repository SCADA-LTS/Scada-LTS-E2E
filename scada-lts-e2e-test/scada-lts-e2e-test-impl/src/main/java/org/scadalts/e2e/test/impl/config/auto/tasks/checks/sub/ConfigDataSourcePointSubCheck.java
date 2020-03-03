package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
public class ConfigDataSourcePointSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourcePointCriteria dataSourcePointCriteria;

    @Override
    public void execute() {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        DataSourceCriteria dataSourceCriteria = dataSourcePointCriteria.getDataSource();
        DataPointCriteria dataPointCriteria = dataSourcePointCriteria.getDataPoint();

        assertExists(dataSourcesPage, dataSourceCriteria);

        EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria);

        boolean dataSourceEnabled = editDataSourceWithPointListPage.isEnableDataSource();
        boolean dataPointEnabled = editDataSourceWithPointListPage.isEnableDataPoint(dataPointCriteria);

        assertEquals(dataSourceCriteria.isEnabled(), dataSourceEnabled);
        assertEquals(dataPointCriteria.isEnabled(), dataPointEnabled);

        EditDataPointPage editDataPointPage = editDataSourceWithPointListPage.openDataPointEditor(dataPointCriteria);

        boolean settable = editDataPointPage.isSettable();
        assertEquals(dataPointCriteria.isSettable(), settable);
    }
}
