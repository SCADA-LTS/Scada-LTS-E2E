package org.scadalts.e2e.test.impl.creators;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.List;

@Log4j2
public class InternalDataPointObjectsCreator extends DataPointObjectsCreator<UpdateDataSourceCriteria, InternalDataPointCriteria> {

    public InternalDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull UpdateDataSourceCriteria dataSourceCriteria, @NonNull InternalDataPointCriteria... dataPoints) {
        super(navigationPage, dataSourceCriteria, dataPoints);
    }

    public InternalDataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull UpdateDataSourceCriteria dataSourceCriteria, @NonNull List<InternalDataPointCriteria> dataPoints) {
        super(navigationPage, dataSourceCriteria, dataPoints);
    }


    public InternalDataPointObjectsCreator(@NonNull NavigationPage navigationPage,
                                           @NonNull InternalDataSourcePointCriteria dataSourcePointCriteria,
                                           InternalDataSourcePointCriteria... dataSourcePointCriterias) {
        super(navigationPage, dataSourcePointCriteria, dataSourcePointCriterias);
    }

    @Override
    public EditDataPointPage createDataPoint(EditDataSourceWithPointListPage page, InternalDataPointCriteria dataPoint) {

        logger.info("creating object: {}, type: {}, xid: {}, class: {}", dataPoint.getIdentifier().getValue(), dataPoint.getIdentifier().getType(),
                dataPoint.getXid().getValue(), dataPoint.getClass().getSimpleName());

        EditDataPointPage resultPage = page.addDataPoint()
                .setName(dataPoint.getIdentifier())
                .setXid(dataPoint.getXid())
                .setInternalAttributeId(dataPoint.getAttributeType())
                .save();

        logger.info("created object: {}, type: {}, xid: {}, class: {}", dataPoint.getIdentifier().getValue(), dataPoint.getIdentifier().getType(),
                dataPoint.getXid().getValue(), dataPoint.getClass().getSimpleName());

        return resultPage;
    }
}
