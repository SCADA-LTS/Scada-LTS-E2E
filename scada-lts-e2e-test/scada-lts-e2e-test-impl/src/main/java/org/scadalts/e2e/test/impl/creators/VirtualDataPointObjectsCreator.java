package org.scadalts.e2e.test.impl.creators;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.List;

@Log4j2
public class VirtualDataPointObjectsCreator extends DataPointObjectsCreator<UpdateDataSourceCriteria, VirtualDataPointCriteria> {

    public VirtualDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull UpdateDataSourceCriteria dataSourceCriteria, @NonNull VirtualDataPointCriteria... dataPoints) {
        super(navigationPage, dataSourceCriteria, dataPoints);
    }

    public VirtualDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull UpdateDataSourceCriteria dataSourceCriteria, @NonNull List<VirtualDataPointCriteria> dataPoints) {
        super(navigationPage, dataSourceCriteria, dataPoints);
    }

    public VirtualDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull VirtualDataSourcePointCriteria dataSourcePoint) {
        super(navigationPage, dataSourcePoint);
    }

    @Override
    public EditDataPointPage createDataPoint(EditDataSourceWithPointListPage page, VirtualDataPointCriteria dataPoint) {

        logger.info("creating object: {}, type: {}, xid: {}, class: {}", dataPoint.getIdentifier().getValue(), dataPoint.getIdentifier().getType(),
                dataPoint.getXid().getValue(), dataPoint.getClass().getSimpleName());

        EditDataPointPage resultPage = page.addDataPoint()
                .setName(dataPoint.getIdentifier())
                .setXid(dataPoint.getXid())
                .setSettable(dataPoint.isSettable())
                .setDataPointType(dataPoint.getIdentifier().getType())
                .setChangeType(dataPoint.getChangeType())
                .setStartValue(dataPoint)
                .save();

        logger.info("created object: {}, type: {}, xid: {}, class: {}", dataPoint.getIdentifier().getValue(), dataPoint.getIdentifier().getType(),
                dataPoint.getXid().getValue(), dataPoint.getClass().getSimpleName());

        return resultPage;
    }
}
