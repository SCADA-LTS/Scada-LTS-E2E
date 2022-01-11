package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourcePage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

public class EditorUtil {

    private static NavigationPage navigationPage;

    public static void init(NavigationPage navigation) {
        navigationPage = navigation;
    }

    public static EditDataSourceWithPointListPage openDataSourceEditor(DataSourceCriteria criteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria.getIdentifier());
    }

    public static EditDataSourcePage openDataSourceCreator() {
        return navigationPage.openDataSources().openDataSourceCreator(DataSourceType.VIRTUAL_DATA_SOURCE);
    }

    public static EditDataSourceWithPointListPage openDataSourceEditor(DataSourcesPage opened, DataSourceCriteria targetCriteria) {
        return opened.openDataSourceEditor(targetCriteria.getIdentifier());
    }

    public static EditDataPointPage openDataPointEditor(DataSourcesPage opened, DataSourceCriteria criteria, DataPointCriteria targetCriteria) {
        return opened.openDataSourceEditor(criteria).openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(EditDataSourceWithPointListPage opened, DataPointCriteria targetCriteria) {
        return opened.openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(DataSourceCriteria criteria, DataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).openDataPointEditor(targetCriteria);
    }
}
