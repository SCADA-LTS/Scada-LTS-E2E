package org.scadalts.e2e.test.impl.creators;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class DataPointObjectsCreator implements CreatorObject<EditDataSourceWithPointListPage, EditDataSourceWithPointListPage> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceCriteria dataSourceCriteria;
    private final @NonNull DataPointCriteria[] dataPointCriterias;
    private DataSourcesPage dataSourcesPage;

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourceCriteria dataSourceCriteria) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriterias = new DataPointCriteria[]{DataPointCriteria.binaryAlternate()};
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourceCriteria dataSourceCriteria, @NonNull DataPointCriteria... dataPointCriterias) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriterias = dataPointCriterias;
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourcePointCriteria dataSourcePointCriteria) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourcePointCriteria.getDataSource();
        this.dataPointCriterias = new DataPointCriteria[]{dataSourcePointCriteria.getDataPoint()};
    }

    @Override
    public EditDataSourceWithPointListPage deleteObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        for (DataPointCriteria dataPointCriteria : dataPointCriterias) {
            if(editDataSourceWithPointListPage.containsObject(dataPointCriteria)) {
                _deleteDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
            }
        }
        return editDataSourceWithPointListPage;
    }

    @Override
    public EditDataSourceWithPointListPage createObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        return createObjects(editDataSourceWithPointListPage);
    }

    public EditDataSourceWithPointListPage createObjects(EditDataSourceWithPointListPage editDataSourceWithPointListPage) {
        for (DataPointCriteria dataPointCriteria : dataPointCriterias) {
            if(!editDataSourceWithPointListPage.containsObject(dataPointCriteria)) {
                _createDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
            }
        }
        return editDataSourceWithPointListPage.enableAllDataPoint();
    }

    @Override
    public EditDataSourceWithPointListPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSourceCriteria);
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria);
    }

    private EditDataPointPage _createDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria criteria) {
        logger.info("create object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(), criteria.getType(),
                criteria.getXid().getValue());
        return page.addDataPoint()
                .setDataPointName(criteria.getIdentifier())
                .selectDataPointType(criteria.getType())
                .setSettable(criteria.isSettable())
                .selectChangeType(criteria.getChangeType())
                .setDataPointXid(criteria.getXid())
                .setStartValue(criteria)
                .saveDataPoint();
    }

    private EditDataSourceWithPointListPage _deleteDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria criteria) {
        logger.debug("delete object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(), criteria.getType(),
                criteria.getXid().getValue());
        return page.openDataPointEditor(criteria)
                .deleteDataPoint();
    }
}
