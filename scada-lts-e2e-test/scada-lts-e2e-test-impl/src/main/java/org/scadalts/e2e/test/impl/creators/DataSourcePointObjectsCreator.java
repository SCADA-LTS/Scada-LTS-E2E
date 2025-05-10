package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.Map;
import java.util.stream.Collectors;

import static org.scadalts.e2e.page.core.criterias.Tag.tr;
import static org.scadalts.e2e.page.core.xpaths.XpathAttribute.clazz;

@Log4j2
public abstract class DataSourcePointObjectsCreator<S extends DataSourceCriteria, P extends DataPointCriteria>
        implements CreatorObject<DataSourcesPage, DataSourcesPage> {

    private NavigationPage navigationPage;
    private final Map<S, DataPointObjectsCreator<S, P>> dataPointCreators;

    private static final NodeCriteria ALL_DATA_SOURCES = NodeCriteria.every(tr(), clazz("row"));

    @Getter
    private DataSourcesPage dataSourcesPage;

    public DataSourcePointObjectsCreator(NavigationPage navigationPage, Map<S, DataPointObjectsCreator<S, P>> dataPointCreators) {
        this.dataPointCreators = dataPointCreators;
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
        //deleteDataPoints();
        return _deleteDataPointsAndDataSources(dataPointCreators);
    }

    public DataSourcesPage deleteDataPoints() {
        return _deleteDataPoints(dataPointCreators);
    }


    public EditDataSourceWithPointListPage createDataSources() {
        EditDataSourceWithPointListPage page = new EditDataSourceWithPointListPage();
        DataSourcesPage dataSourcesPage = openPage();
        for (S criteria : dataPointCreators.keySet()) {
            page = createDataSource(dataSourcesPage, criteria);
        }
        return page;
    }

    public static DataSourcesPage deleteAllDataSourcesTest(NavigationPage navigationPage) {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        NodeCriteria nodeCriteria = NodeCriteria.exactlyTypeAny(new DataSourceIdentifier("ds_test",
                DataSourceType.NONE), tr(), clazz("row"));
        dataSourcesPage.deleteAllDataSourcesMatching(nodeCriteria);
        return dataSourcesPage;
    }

    public static DataSourcesPage disableAllDataSources(NavigationPage navigationPage) {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        dataSourcesPage.disableAllDataSourcesMatching(ALL_DATA_SOURCES);
        return dataSourcesPage;
    }

    public static DataSourcesPage enableAllDataSources(NavigationPage navigationPage) {
        DataSourcesPage dataSourcesPage = navigationPage.openDataSources();
        dataSourcesPage.enableAllDataSourcesMatching(ALL_DATA_SOURCES);
        return dataSourcesPage;
    }

    @Override
    public void reload() {
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }

    private DataSourcesPage _deleteDataPointsAndDataSources(Map<S, DataPointObjectsCreator<S, P>> criteriaMap) {
        DataSourcesPage page = openPage();
        for (S criteria : criteriaMap.keySet()) {
            if(page.containsObject(criteria.getIdentifier())) {

                logger.info("deleting object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());

                page.deleteDataSource(criteria.getIdentifier());

                logger.info("deleted object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getIdentifier().getType(), criteria.getXid().getValue(), criteria.getClass().getSimpleName());
            }
        }
        return page;
    }

    private DataSourcesPage _deleteDataPoints(Map<S, DataPointObjectsCreator<S, P>> criteriaMap) {
        DataSourcesPage page = openPage();
        for (S criteria : criteriaMap.keySet()) {
            if(page.containsObject(criteria)) {
                DataPointObjectsCreator<S, P> creator = criteriaMap.get(criteria);
                creator.deleteObjects();
            } else {
                criteriaMap.remove(criteria);
            }
        }
        return page;
    }

    protected abstract EditDataSourceWithPointListPage createDataSource(DataSourcesPage page, S criteria);

    private DataSourcesPage _createDataSourcesAndPoints() {
        DataSourcesPage dataSourcesPage = openPage();
        for (S criteria : dataPointCreators.keySet()) {
            DataPointObjectsCreator<S, P> creator = dataPointCreators.get(criteria);
            if(!dataSourcesPage.containsObject(criteria.getIdentifier())) {
                EditDataSourceWithPointListPage editDataSourceWithPointListPage = createDataSource(dataSourcesPage, criteria);
                creator.createObjects(editDataSourceWithPointListPage);
            } else {
                EditDataSourceWithPointListPage editDataSourceWithPointListPage = dataSourcesPage.openDataSourceEditor(criteria.getIdentifier());
                creator.createObjects(editDataSourceWithPointListPage);
            }
            dataSourcesPage.reopen();
        }
        return dataSourcesPage;
    }
}
