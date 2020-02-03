package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

public abstract class DataSourceTestsUtil {

    private final static NavigationPage navigationPage = E2eAbstractRunnable.getNavigationPage();
    private final static DataSourcesPage dataSourcesPage = navigationPage.openDataSources();

    public static EditDataSourceWithPointListPage addDataSource(DataSourceCriteria params) {

        return dataSourcesPage.reopen()
                .openDataSourceCreator(params.getType())
                .selectUpdatePeriodType(UpdatePeriodType.SECOUND)
                .setUpdatePeriods(33)
                .setDataSourceName(params.getName())
                .saveDataSource();
    }

    public static EditDataPointPage addDataPoint(DataSourceCriteria sourceParams,
                                                 DataPointCriteria pointParams,
                                                 String value) {
        return dataSourcesPage.reopen()
                .openDataSourceEditor(sourceParams)
                .addDataPoint()
                .setDataPointName(pointParams.getName())
                .selectDataPointType(pointParams.getType())
                .enableSettable()
                .selectChangeType(ChangeType.ALTERNATE)
                .setStartValue(value)
                .saveDataPoint()
                .enableDataPoint(pointParams)
                .openDataPointEditor(pointParams);
    }

    public static EditDataSourceWithPointListPage addDataPoints(DataSourceCriteria sourceParams,
                                                 DataPointCriteria[] pointParams,
                                                 String value) {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage =
                dataSourcesPage.reopen()
                .openDataSourceEditor(sourceParams);

        for(DataPointCriteria pointParam: pointParams) {
            editDataSourceWithPointListPage.addDataPoint()
                    .setDataPointName(pointParam.getName())
                    .selectDataPointType(pointParam.getType())
                    .enableSettable()
                    .selectChangeType(ChangeType.ALTERNATE)
                    .setStartValue(value)
                    .saveDataPoint()
                    .enableDataPoint(pointParam);
        }

        return editDataSourceWithPointListPage;
    }

    public static void deteleDataSource(DataSourceCriteria dataSourceParams) {
        dataSourcesPage.reopen()
                .deleteDataSource(dataSourceParams);
    }

    public static DataSourcesPage openDataSourcesPage() {
        return dataSourcesPage.reopen();
    }

}
