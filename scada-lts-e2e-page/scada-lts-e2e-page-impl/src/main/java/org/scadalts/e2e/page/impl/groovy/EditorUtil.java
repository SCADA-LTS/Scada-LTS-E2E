package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.impl.criterias.InternalDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
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

    public static EditDataSourceWithPointListPage openDataSourceEditor(UpdateDataSourceCriteria criteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria.getIdentifier());
    }

    public static EditDataSourcePage openDataSourceCreator() {
        return navigationPage.openDataSources().openDataSourceCreator(DataSourceType.VIRTUAL_DATA_SOURCE);
    }

    public static EditDataSourceWithPointListPage openDataSourceEditor(DataSourcesPage opened, UpdateDataSourceCriteria targetCriteria) {
        return opened.openDataSourceEditor(targetCriteria.getIdentifier());
    }

    public static EditDataPointPage openDataPointEditor(DataSourcesPage opened, UpdateDataSourceCriteria criteria, VirtualDataPointCriteria targetCriteria) {
        return opened.openDataSourceEditor(criteria).openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(EditDataSourceWithPointListPage opened, VirtualDataPointCriteria targetCriteria) {
        return opened.openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(UpdateDataSourceCriteria criteria, VirtualDataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(DataSourcesPage opened, UpdateDataSourceCriteria criteria, InternalDataPointCriteria targetCriteria) {
        return opened.openDataSourceEditor(criteria).openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(EditDataSourceWithPointListPage opened, InternalDataPointCriteria targetCriteria) {
        return opened.openDataPointEditor(targetCriteria);
    }

    public static EditDataPointPage openDataPointEditor(UpdateDataSourceCriteria criteria, InternalDataPointCriteria targetCriteria) {
        return navigationPage.openDataSources().openDataSourceEditor(criteria).openDataPointEditor(targetCriteria);
    }
}
