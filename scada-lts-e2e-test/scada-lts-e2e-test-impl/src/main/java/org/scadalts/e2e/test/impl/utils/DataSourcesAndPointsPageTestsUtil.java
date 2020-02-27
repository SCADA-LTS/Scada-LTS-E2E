package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.core.utils.Cleanable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataSourcesAndPointsPageTestsUtil implements Cleanable {

    private final NavigationPage navigationPage;
    private final Map<DataSourceCriteria, DataPointCriteria[]> criteriaMap;

    @Getter
    private DataSourcesPage dataSourcesPage;

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = new HashMap<>();
        this.criteriaMap.put(dataSourceCriteria, dataPointCriteria);
        this.navigationPage = navigationPage;
    }

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage, Map<DataSourceCriteria, DataPointCriteria[]> criteriaMap) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = criteriaMap;
        this.navigationPage = navigationPage;
    }

    public DataSourcesAndPointsPageTestsUtil(NavigationPage navigationPage, DataPointCriteria... dataPointCriteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteriaMap = new HashMap<>();
        this.criteriaMap.put(DataSourceCriteria.virtualDataSourceSecound(), dataPointCriteria.length == 0 ?
                new DataPointCriteria[]{DataPointCriteria.binaryAlternate()} : dataPointCriteria);
        this.navigationPage = navigationPage;
    }

    public EditDataSourceWithPointListPage init() {
        return _addDataSourcesAndPoints();
    }

    @Override
    public void clean() {
        deleteDataPoints();
        deleteDataSources();
    }

    public EditDataSourceWithPointListPage addDataSources() {
        EditDataSourceWithPointListPage page = new EditDataSourceWithPointListPage();
        for (DataSourceCriteria criteria : criteriaMap.keySet()) {
            page = _addDataSource(criteria);
        }
        return page;
    }

    public EditDataSourceWithPointListPage addDataPoints() {
        EditDataPointPage page = new EditDataPointPage();
        for (DataSourceCriteria dataSourceParam : criteriaMap.keySet()) {
            EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.reopen()
                    .openDataSourceEditor(dataSourceParam);
            for (DataPointCriteria dataPointParam : criteriaMap.get(dataSourceParam)) {
                page = _addDataPoint(editDataSourceWithPointListPage, dataPointParam);
            }
        }
        return page.enableAllDataPoint();
    }

    public void deleteDataSources() {
        DataSourcesPage page = openDataSourcesPage();
        for (DataSourceCriteria dataSourceParam : criteriaMap.keySet()) {
            if(page.containsObject(dataSourceParam))
                page.deleteDataSource(dataSourceParam);
        }
    }

    public void deleteDataPoints() {
        DataSourcesPage page = openDataSourcesPage();
        for (DataSourceCriteria dataSourceParam : criteriaMap.keySet()) {
            if(page.containsObject(dataSourceParam)) {
                EditDataSourceWithPointListPage editDataSourceWithPointListPage = page
                        .openDataSourceEditor(dataSourceParam);
                for (DataPointCriteria dataPointParam : criteriaMap.get(dataSourceParam)) {
                    if (editDataSourceWithPointListPage.containsObject(dataPointParam))
                        editDataSourceWithPointListPage.openDataPointEditor(dataPointParam)
                                .deleteDataPoint();
                }
                page.reopen();
            }
        }
    }

    public DataSourcesPage openDataSourcesPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage;
        }
        return dataSourcesPage.reopen();
    }

    private EditDataPointPage _addDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria pointParams) {
        return page.addDataPoint()
                .setDataPointName(pointParams.getIdentifier())
                .selectDataPointType(pointParams.getType())
                .enableSettable()
                .selectChangeType(pointParams.getChangeType())
                .setStartValue(pointParams)
                .saveDataPoint()
                .openDataPointEditor(pointParams);
    }

    private EditDataSourceWithPointListPage _addDataSource(DataSourceCriteria params) {
        return openDataSourcesPage()
                .openDataSourceCreator(params.getType())
                .selectUpdatePeriodType(params.getUpdatePeriodType())
                .setUpdatePeriods(33)
                .setDataSourceName(params.getIdentifier())
                .saveDataSource()
                .enableDataSource();
    }

    private EditDataSourceWithPointListPage _addDataSourcesAndPoints() {
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
