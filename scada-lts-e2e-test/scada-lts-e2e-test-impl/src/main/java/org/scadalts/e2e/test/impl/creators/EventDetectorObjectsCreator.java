package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
public class EventDetectorObjectsCreator implements CreatorObject<DataSourcesPage, DataPointPropertiesPage> {

    private NavigationPage navigationPage;
    private DataSourcesPage dataSourcesPage;
    private final EventDetectorCriteria eventDetectorCriteria;
    private DataSourcePointObjectsCreator<?, ?> dataSourcePointObjectsCreator;

    public EventDetectorObjectsCreator(NavigationPage navigationPage,
                                       EventDetectorCriteria eventDetectorCriteria) {
        this.navigationPage = navigationPage;
        this.eventDetectorCriteria = eventDetectorCriteria;

        if (eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint() instanceof InternalDataPointCriteria) {
            this.dataSourcePointObjectsCreator = new InternalDataSourcePointObjectsCreator(navigationPage,
                    (UpdateDataSourceCriteria) eventDetectorCriteria.getDataSourcePointCriteria().getDataSource(),
                    (InternalDataPointCriteria) eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint());
        } else if (eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint() instanceof VirtualDataPointCriteria) {
            this.dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage,
                    (UpdateDataSourceCriteria) eventDetectorCriteria.getDataSourcePointCriteria().getDataSource(),
                    (VirtualDataPointCriteria) eventDetectorCriteria.getDataSourcePointCriteria().getDataPoint());
        }
    }

    @Override
    public DataSourcesPage deleteObjects() {
        DataPointPropertiesPage dataPointPropertiesPage = openPage();
        if(dataPointPropertiesPage.containsObject(eventDetectorCriteria.getIdentifier())) {

            logger.info("deleting object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());

            dataPointPropertiesPage.deleteEventDetector(eventDetectorCriteria)
                    .saveDataPoint();

            logger.info("deleted object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());
        }
        return navigationPage.openDataSources();
    }

    public DataSourcesPage deleteDataSources() {
        return dataSourcePointObjectsCreator.deleteObjects();
    }

    public DataPointPropertiesPage deleteEventDetectors() {
        DataPointPropertiesPage dataPointPropertiesPage = openPage();
        if(dataPointPropertiesPage.containsObject(eventDetectorCriteria.getIdentifier())) {

            logger.info("deleting object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());

            dataPointPropertiesPage.deleteEventDetector(eventDetectorCriteria)
                    .saveDataPoint();

            logger.info("deleted object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());
        }
        return dataPointPropertiesPage;
    }

    @Override
    public DataPointPropertiesPage createObjects() {
        dataSourcePointObjectsCreator.createObjects();
        DataPointPropertiesPage dataPointPropertiesPage = openPage();
        if(!dataPointPropertiesPage.containsObject(eventDetectorCriteria.getIdentifier())) {

            logger.info("creating object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());

            dataPointPropertiesPage.selectEventDetectorType(eventDetectorCriteria.getIdentifier().getType())
                    .addEventDetector()
                    .setEventDetectorAlias(eventDetectorCriteria.getIdentifier())
                    .setEventDetectorXid(eventDetectorCriteria.getXid())
                    .selectEventDetectorAlarmLevel(eventDetectorCriteria.getAlarmLevel())
                    .saveDataPoint();

            logger.info("created object: {}, type: {}, xid: {}, class: {}", eventDetectorCriteria.getIdentifier().getValue(),
                    eventDetectorCriteria.getIdentifier().getType(), eventDetectorCriteria.getXid().getValue(),
                    eventDetectorCriteria.getClass().getSimpleName());

        }
        return dataPointPropertiesPage;
    }

    @Override
    public DataPointPropertiesPage openPage() {
        DataSourcePointCriteria<?,?> dataSourcePointCriteria = eventDetectorCriteria.getDataSourcePointCriteria();
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

    @Override
    public void reload() {
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }
}
