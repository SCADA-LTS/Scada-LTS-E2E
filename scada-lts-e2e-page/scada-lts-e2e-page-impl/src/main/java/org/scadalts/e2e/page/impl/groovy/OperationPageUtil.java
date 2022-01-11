package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

public class OperationPageUtil {

    private static NavigationPage navigationPage;

    public static void init(NavigationPage navigation) {
        navigationPage = navigation;
    }


    public static EditDataSourceWithPointListPage enableDataPoint(DataSourceCriteria criteria, DataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).enableDataPoint(targetCriteria);
    }

    public static EditDataSourceWithPointListPage disableDataPoint(DataSourceCriteria criteria, DataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).enableDataPoint(targetCriteria);
    }

    public static DataSourcesPage disableDataSource(DataSourceCriteria criteria) {
        return navigationPage.openDataSources().disableDataSource(criteria.getIdentifier());
    }
}
