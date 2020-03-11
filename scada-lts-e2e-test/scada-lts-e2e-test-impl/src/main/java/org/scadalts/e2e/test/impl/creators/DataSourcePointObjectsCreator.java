package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.scadalts.e2e.page.impl.criterias.CriteriaUtil.createCriteriaStructure;

@Log4j2
public class DataSourcePointObjectsCreator implements CreatorObject<DataSourcesPage, DataSourcesPage> {

    private final NavigationPage navigationPage;
    private final Map<DataSourceCriteria, DataPointObjectsCreator> dataSources;

    @Getter
    private DataSourcesPage dataSourcesPage;

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, DataSourceCriteria dataSourceCriteria, DataPointCriteria... dataPointCriteria) {
        this.dataSources = _convert(navigationPage, createCriteriaStructure(dataSourceCriteria, dataPointCriteria));
        this.navigationPage = navigationPage;
    }

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, DataPointCriteria... dataPointCriterias) {
        this.dataSources = _convert(navigationPage, createCriteriaStructure(dataPointCriterias));
        this.navigationPage = navigationPage;
    }

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, DataSourcePointCriteria... dataPointCriterias) {
        this.dataSources = _convert(navigationPage, createCriteriaStructure(dataPointCriterias));
        this.navigationPage = navigationPage;
    }

    @Override
    public DataSourcesPage createObjects() {
        return _createDataSourcesAndPoints();
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
        return _deleteDataPointsAndDataSources(dataSources);
    }

    public EditDataSourceWithPointListPage createDataSources() {
        EditDataSourceWithPointListPage page = new EditDataSourceWithPointListPage();
        DataSourcesPage dataSourcesPage = openPage();
        for (DataSourceCriteria criteria : dataSources.keySet()) {
            page = _createDataSource(dataSourcesPage, criteria);
        }
        return page;
    }

    private DataSourcesPage _deleteDataPointsAndDataSources(Map<DataSourceCriteria, DataPointObjectsCreator> criteriaMap) {
        DataSourcesPage page = openPage();
        for (DataSourceCriteria criteria : criteriaMap.keySet()) {
            if(page.containsObject(criteria)) {
                logger.info("delete object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                page.deleteDataSource(criteria);
            }
        }
        return page;
    }

    private EditDataSourceWithPointListPage _createDataSource(DataSourcesPage page, DataSourceCriteria criteria) {
        logger.info("create object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(), criteria.getType(),
                criteria.getXid().getValue());
        return page.openDataSourceCreator(criteria.getType())
                .selectUpdatePeriodType(criteria.getUpdatePeriodType())
                .setUpdatePeriods(criteria.getUpdatePeriodValue())
                .setDataSourceName(criteria.getIdentifier())
                .setDataSourceXid(criteria.getXid())
                .saveDataSource()
                .enableDataSource(true);
    }

    private DataSourcesPage _createDataSourcesAndPoints() {
        DataSourcesPage dataSourcesPage = openPage();
        for (DataSourceCriteria criteria : dataSources.keySet()) {
            if(!dataSourcesPage.containsObject(criteria)) {
                EditDataSourceWithPointListPage editDataSourceWithPointListPage = _createDataSource(dataSourcesPage, criteria);
                DataPointObjectsCreator creator = dataSources.get(criteria);
                creator.createObjects(editDataSourceWithPointListPage);
                dataSourcesPage.reopen();
            }
        }
        return dataSourcesPage;
    }

    private static Map<DataSourceCriteria, DataPointObjectsCreator> _convert(NavigationPage navigationPage, Map<DataSourceCriteria, List<DataPointCriteria>> sources) {
        return sources.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        new DataPointObjectsCreator(navigationPage, entry.getKey(), entry.getValue()
                                .toArray(new DataPointCriteria[]{}))));
    }
}
