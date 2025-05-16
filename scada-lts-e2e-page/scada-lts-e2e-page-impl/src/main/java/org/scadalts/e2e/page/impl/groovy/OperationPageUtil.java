package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

public class OperationPageUtil {

    private static NavigationPage navigationPage;

    public static void init(NavigationPage navigation) {
        navigationPage = navigation;
    }


    public static EditDataSourceWithPointListPage enableDataPoint(UpdateDataSourceCriteria criteria, VirtualDataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).enableDataPoint(targetCriteria);
    }

    public static EditDataSourceWithPointListPage disableDataPoint(UpdateDataSourceCriteria criteria, VirtualDataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).enableDataPoint(targetCriteria);
    }

    public static DataSourcesPage disableDataSource(UpdateDataSourceCriteria criteria) {
        return navigationPage.openDataSources().disableDataSource(criteria.getIdentifier());
    }
}
