package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;

@Data
public class ConfigForTestDataPointDetailsCheck implements Check<DataPointDetailsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond(new DataSourceIdentifier(TestImplConfiguration.dataSourceName));
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(new DataPointIdentifier(TestImplConfiguration.dataPointName));
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);

        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.openDataSourceEditor(dataSourceCriteria);

        editDataSourceWithPointListPage.openDataPointEditor(dataPointCriteria);

        WatchListPage watchListPage = navigationPage.openWatchList();
        watchListPage.addToWatchList(dataSourcePointCriteria);
    }

    @Override
    public Class<DataPointDetailsCheckTestsSuite> getClassTarget() {
        return DataPointDetailsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
