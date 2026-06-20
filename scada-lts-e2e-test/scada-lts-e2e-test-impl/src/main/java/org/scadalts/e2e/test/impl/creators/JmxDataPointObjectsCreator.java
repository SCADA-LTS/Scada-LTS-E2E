package org.scadalts.e2e.test.impl.creators;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.List;

@Log4j2
public class JmxDataPointObjectsCreator extends DataPointObjectsCreator<JmxDataSourceCriteria, JmxDataPointCriteria> {

    public JmxDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull JmxDataSourceCriteria dataSourceCriteria, @NonNull JmxDataPointCriteria... dataPoints) {
        super(navigationPage, dataSourceCriteria, dataPoints);
    }

    public JmxDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull JmxDataSourceCriteria dataSourceCriteria, @NonNull List<JmxDataPointCriteria> dataPoints) {
        super(navigationPage, dataSourceCriteria, dataPoints);
    }


    public JmxDataPointObjectsCreator(@NonNull NavigationPage navigationPage,
                                      @NonNull JmxDataSourcePointCriteria dataSourcePointCriteria,
                                      JmxDataSourcePointCriteria... dataSourcePointCriterias) {
        super(navigationPage, dataSourcePointCriteria, dataSourcePointCriterias);
    }

    @Override
    public EditDataPointPage createDataPoint(EditDataSourceWithPointListPage page, JmxDataPointCriteria dataPoint) {

        logger.info("creating object: {}, type: {}, xid: {}, class: {}", dataPoint.getIdentifier().getValue(), dataPoint.getIdentifier().getType(),
                dataPoint.getXid().getValue(), dataPoint.getClass().getSimpleName());

        EditDataPointPage resultPage = page.addDataPoint()
                .setName(dataPoint.getIdentifier())
                .setXid(dataPoint.getXid())
                .setObjectName(dataPoint.getObjectName())
                .setAttributeName(dataPoint.getAttributeName())
                .setCompositeItemName(dataPoint.getCompositeItemName())
                .setDataPointType(dataPoint.getIdentifier().getType())
                .setSettable(dataPoint.isSettable())
                .save();

        logger.info("created object: {}, type: {}, xid: {}, class: {}", dataPoint.getIdentifier().getValue(), dataPoint.getIdentifier().getType(),
                dataPoint.getXid().getValue(), dataPoint.getClass().getSimpleName());

        return resultPage;
    }
}
