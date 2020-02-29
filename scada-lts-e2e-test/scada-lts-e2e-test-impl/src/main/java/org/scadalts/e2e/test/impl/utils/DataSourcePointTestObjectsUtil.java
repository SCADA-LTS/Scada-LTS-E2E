package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.scadalts.e2e.page.impl.criterias.CriteriaUtil.createCriteriaStructure;

public class DataSourcePointTestObjectsUtil implements TestObjectsUtilible<DataSourcesPage, EditDataSourceWithPointListPage> {

    private final NavigationPage navigationPage;
    private final Map<DataSourceCriteria, List<DataPointCriteria>> criteriaMap;

    @Getter
    private DataSourcesPage dataSourcesPage;

    public DataSourcePointTestObjectsUtil(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = createCriteriaStructure(dataSourceCriteria, dataPointCriteria);
        this.navigationPage = navigationPage;
    }

    public DataSourcePointTestObjectsUtil(NavigationPage navigationPage, Map<DataSourceCriteria, List<DataPointCriteria>> criteriaMap) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = criteriaMap;
        this.navigationPage = navigationPage;
    }

    public DataSourcePointTestObjectsUtil(NavigationPage navigationPage, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = createCriteriaStructure(dataPointCriteria);
        this.navigationPage = navigationPage;
    }

    public DataSourcePointTestObjectsUtil(NavigationPage navigationPage, DataSourcePointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = createCriteriaStructure(dataPointCriteria);
        this.navigationPage = navigationPage;
    }

    @Override
    public EditDataSourceWithPointListPage createObjects() {
        return _addDataSourcesAndPoints(criteriaMap);
    }

    @Override
    public DataSourcesPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage;
        }
        return dataSourcesPage.reopen();
    }

    @Override
    public DataSourcesPage deleteObjects() {
        return _deleteDataPointsAndDataSources(criteriaMap);
    }

    public EditDataSourceWithPointListPage createDataSources() {
        EditDataSourceWithPointListPage page = new EditDataSourceWithPointListPage();
        for (DataSourceCriteria criteria : criteriaMap.keySet()) {
            page = _addDataSource(criteria);
        }
        return page;
    }

    private DataSourcesPage _deleteDataPointsAndDataSources(Map<DataSourceCriteria, List<DataPointCriteria>> criteriaMap) {
        DataSourcesPage page = openPage();
        for (DataSourceCriteria dataSourceParam : criteriaMap.keySet()) {
            if(page.containsObject(dataSourceParam)) {
                EditDataSourceWithPointListPage editDataSourceWithPointListPage = page
                        .openDataSourceEditor(dataSourceParam);
                for (DataPointCriteria dataPointParam : criteriaMap.get(dataSourceParam)) {
                    if (editDataSourceWithPointListPage.containsObject(dataPointParam))
                        editDataSourceWithPointListPage.openDataPointEditor(dataPointParam)
                                .deleteDataPoint();
                }
                page.reopen()
                        .deleteDataSource(dataSourceParam);
            }
        }
        return page;
    }

    private EditDataPointPage _addDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria pointParams) {
        return page.addDataPoint()
                .setDataPointName(pointParams.getIdentifier())
                .selectDataPointType(pointParams.getType())
                .enableSettable()
                .selectChangeType(pointParams.getChangeType())
                .setDataPointXid(pointParams.getXid())
                .setStartValue(pointParams)
                .saveDataPoint()
                .openDataPointEditor(pointParams);
    }

    private EditDataSourceWithPointListPage _addDataSource(DataSourceCriteria params) {
        return openPage()
                .openDataSourceCreator(params.getType())
                .selectUpdatePeriodType(params.getUpdatePeriodType())
                .setUpdatePeriods(params.getUpdatePeriodValue())
                .setDataSourceName(params.getIdentifier())
                .setDataSourceXid(params.getXid())
                .saveDataSource()
                .enableDataSource();
    }

    private EditDataSourceWithPointListPage _addDataSourcesAndPoints(Map<DataSourceCriteria, List<DataPointCriteria>> criteriaMap) {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = new EditDataSourceWithPointListPage();
        for (DataSourceCriteria dataSourceParams : criteriaMap.keySet()) {
            editDataSourceWithPointListPage = _addDataSource(dataSourceParams);
            for (DataPointCriteria pointParams : criteriaMap.get(dataSourceParams)) {
                _addDataPoint(editDataSourceWithPointListPage, pointParams);
            }
            editDataSourceWithPointListPage.enableAllDataPoint();
        }
        return editDataSourceWithPointListPage;
    }
}
