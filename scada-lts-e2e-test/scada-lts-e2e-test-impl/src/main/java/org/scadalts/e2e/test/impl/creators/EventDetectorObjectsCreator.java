package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class EventDetectorObjectsCreator implements CreatorObject<DataPointPropertiesPage, DataPointPropertiesPage> {

    private final NavigationPage navigationPage;
    private DataSourcesPage dataSourcesPage;
    private final EventDetectorCriteria eventDetectorCriteria;

    public EventDetectorObjectsCreator(NavigationPage navigationPage, EventDetectorCriteria eventDetectorCriteria) {
        this.navigationPage = navigationPage;
        this.eventDetectorCriteria = eventDetectorCriteria;
    }

    @Override
    public DataPointPropertiesPage deleteObjects() {
        DataPointPropertiesPage dataPointPropertiesPage = openPage();
        if(dataPointPropertiesPage.containsObject(eventDetectorCriteria.getIdentifier())) {
            logger.info("delete object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());
            dataPointPropertiesPage.deleteEventDetector(eventDetectorCriteria)
                    .saveDataPoint();
        }
        return dataPointPropertiesPage;
    }

    @Override
    public DataPointPropertiesPage createObjects() {
        DataPointPropertiesPage dataPointPropertiesPage = openPage();
        if(!dataPointPropertiesPage.containsObject(eventDetectorCriteria.getIdentifier())) {
            logger.info("create object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());
            dataPointPropertiesPage.selectEventDetectorType(eventDetectorCriteria.getIdentifier().getType())
                    .addEventDetector()
                    .setEventDetectorAlias(eventDetectorCriteria.getIdentifier())
                    .setEventDetectorXid(eventDetectorCriteria.getXid())
                    .selectEventDetectorAlarmLevel(eventDetectorCriteria.getAlarmLevel())
                    .saveDataPoint();

        }
        return dataPointPropertiesPage;
    }

    @Override
    public DataPointPropertiesPage openPage() {
        DataSourcePointCriteria dataSourcePointCriteria = eventDetectorCriteria.getDataSourcePointCriteria();
        DataSourceCriteria dataSourceCriteria = dataSourcePointCriteria.getDataSource();
        DataPointCriteria dataPointCriteria = dataSourcePointCriteria.getDataPoint();
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSourceCriteria.getIdentifier())
                    .openDataPointProperties(dataPointCriteria.getIdentifier());
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier())
                .openDataPointProperties(dataPointCriteria.getIdentifier());
    }
}
