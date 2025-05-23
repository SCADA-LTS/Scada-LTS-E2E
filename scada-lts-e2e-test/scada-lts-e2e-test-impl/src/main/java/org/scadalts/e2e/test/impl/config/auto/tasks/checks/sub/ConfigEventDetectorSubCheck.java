package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.asserts.E2eAssert;
import org.scadalts.e2e.test.impl.utils.MsgUtil;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;
import static org.scadalts.e2e.test.impl.matchers.ContainsObject.containsObject;
import static org.scadalts.e2e.test.impl.utils.MsgUtil.notExistsShouldBeInObjectMsg;

@Data
@Log4j2
public class ConfigEventDetectorSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<EventDetectorCriteria> eventDetectorCriterias;

    @Override
    public void check() {
        logger.info("run... {}", this.getClass().getSimpleName());
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        for (EventDetectorCriteria eventDetectorCriteria : eventDetectorCriterias) {
            DataSourceCriteria dataSourceCriteria = eventDetectorCriteria.getDataSourcePointCriteria().getDataSource();
            DataPointCriteria dataPointCriteria = eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint();

            DataPointPropertiesPage dataPointPropertiesPage = dataSourcesPage
                    .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                    .openDataPointProperties(dataPointCriteria.getIdentifier())
                    .waitOnEventDetectorTable();

            assertExists(dataPointPropertiesPage, eventDetectorCriteria.getIdentifier());
        }
    }
}
