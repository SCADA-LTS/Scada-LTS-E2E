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

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@Data
public class ConfigDataSourcePointSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<DataSourcePointCriteria> dataSourcePointCriterias;

    public ConfigDataSourcePointSubCheck(@NonNull NavigationPage navigationPage, @NonNull Set<DataSourcePointCriteria> dataSourcePointCriterias) {
        this.navigationPage = navigationPage;
        this.dataSourcePointCriterias = dataSourcePointCriterias;
    }

    public ConfigDataSourcePointSubCheck(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria,
                                         DataPointCriteria dataPointCriteria1, DataPointCriteria dataPointCriteria2) {
        this.navigationPage = navigationPage;
        this.dataSourcePointCriterias = _convert(dataSourceCriteria, dataPointCriteria1, dataPointCriteria2);
    }

    @Override
    public void check() {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();

        for (DataSourcePointCriteria dataSourcePointCriteria : dataSourcePointCriterias) {
            DataSourceCriteria dataSourceCriteria = dataSourcePointCriteria.getDataSource();
            DataPointCriteria dataPointCriteria = dataSourcePointCriteria.getDataPoint();

            EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.reopen()
                    .openDataSourceEditor(dataSourceCriteria);

            boolean dataSourceEnabled = editDataSourceWithPointListPage.isEnableDataSource();
            boolean dataPointEnabled = editDataSourceWithPointListPage.isEnableDataPoint(dataPointCriteria);

            assertEquals(dataSourceCriteria.isEnabled(), dataSourceEnabled);
            assertEquals(dataPointCriteria.isEnabled(), dataPointEnabled);

            EditDataPointPage editDataPointPage = editDataSourceWithPointListPage
                    .openDataPointEditor(dataPointCriteria)
                    .waitOnSettableCheckBox();

            boolean settable = editDataPointPage.isSettable();
            assertEquals(dataPointCriteria.isSettable(), settable);
        }
    }

    private Set<DataSourcePointCriteria> _convert(DataSourceCriteria dataSourceCriteria, DataPointCriteria dataPointCriteria1,
                                                  DataPointCriteria dataPointCriteria2) {
        Set<DataSourcePointCriteria> result = new HashSet<>();
        result.add(DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria1));
        result.add(DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria2));
        return result;
    }
}
