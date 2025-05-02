package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@Data
@Log4j2
public class ConfigDataSourcePointSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<VirtualDataSourcePointCriteria> dataSourcePointCriterias;

    public ConfigDataSourcePointSubCheck(@NonNull NavigationPage navigationPage, @NonNull Set<VirtualDataSourcePointCriteria> dataSourcePointCriterias) {
        this.navigationPage = navigationPage;
        this.dataSourcePointCriterias = dataSourcePointCriterias;
    }

    public ConfigDataSourcePointSubCheck(NavigationPage navigationPage, UpdateDataSourceCriteria dataSourceCriteria,
                                         VirtualDataPointCriteria dataPointCriteria1, VirtualDataPointCriteria dataPointCriteria2) {
        this.navigationPage = navigationPage;
        this.dataSourcePointCriterias = _convert(dataSourceCriteria, dataPointCriteria1, dataPointCriteria2);
    }

    @Override
    public void check() {
        logger.info("run... {}", this.getClass().getSimpleName());
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();

        for (VirtualDataSourcePointCriteria dataSourcePointCriteria : dataSourcePointCriterias) {
            UpdateDataSourceCriteria dataSourceCriteria = dataSourcePointCriteria.getDataSource();
            VirtualDataPointCriteria dataPointCriteria = dataSourcePointCriteria.getDataPoint();

            EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.reopen()
                    .waitForObject(dataSourceCriteria.getIdentifier())
                    .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                    .waitOnImgEabledDataSource()
                    .waitForObject(dataPointCriteria.getIdentifier());

            boolean dataSourceEnabled = editDataSourceWithPointListPage.isEnable();
            boolean dataPointEnabled = editDataSourceWithPointListPage.isEnableDataPoint(dataPointCriteria.getIdentifier());

            assertEquals(dataSourceCriteria.isEnabled(), dataSourceEnabled);
            assertEquals(dataPointCriteria.isEnabled(), dataPointEnabled);

            EditDataPointPage editDataPointPage = editDataSourceWithPointListPage
                    .openDataPointEditor(dataPointCriteria.getIdentifier())
                    .waitOnSettableCheckBox();

            boolean settable = editDataPointPage.isSettable();
            assertEquals(dataPointCriteria.isSettable(), settable);
        }
    }

    private Set<VirtualDataSourcePointCriteria> _convert(UpdateDataSourceCriteria dataSourceCriteria, VirtualDataPointCriteria dataPointCriteria1,
                                                         VirtualDataPointCriteria dataPointCriteria2) {
        Set<VirtualDataSourcePointCriteria> result = new HashSet<>();
        result.add(VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria1));
        result.add(VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria2));
        return result;
    }
}
