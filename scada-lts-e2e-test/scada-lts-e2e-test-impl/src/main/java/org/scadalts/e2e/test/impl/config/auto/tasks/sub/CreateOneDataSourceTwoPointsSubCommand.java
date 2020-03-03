package org.scadalts.e2e.test.impl.config.auto.tasks.sub;

import lombok.Builder;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;

@Builder
public class CreateOneDataSourceTwoPointsSubCommand implements SubCommand<DataSourceCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceIdentifier dataSourceIdentifier;
    private final @NonNull DataPointCriteria dataPoint1;
    private final @NonNull DataPointCriteria dataPoint2;

    private CreateOneDataSourceTwoPointsSubCommand(@NonNull NavigationPage navigationPage, @NonNull DataSourceIdentifier dataSourceIdentifier, @NonNull DataPointCriteria dataPoint1, @NonNull DataPointCriteria dataPoint2) {
        this.navigationPage = navigationPage;
        this.dataSourceIdentifier = dataSourceIdentifier;
        this.dataPoint1 = dataPoint1;
        this.dataPoint2 = dataPoint2;
    }

    @Override
    public DataSourceCriteria execute() {

        DataSourceCriteria dataSourceToEventDetector = DataSourceCriteria.virtualDataSourceSecond(dataSourceIdentifier);

        DataSourcePointCriteria dataSourcePoint1 = DataSourcePointCriteria
                .criteria(dataSourceToEventDetector, dataPoint1);
        DataSourcePointCriteria dataSourcePoint2 = DataSourcePointCriteria
                .criteria(dataSourceToEventDetector, dataPoint2);

        DataSourcePointObjectsCreator dataSourcePointTestObjectsUtil =
                new DataSourcePointObjectsCreator(navigationPage, dataSourcePoint1,
                        dataSourcePoint2);

        dataSourcePointTestObjectsUtil.createObjects();

        DataPointObjectsCreator dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage,
                dataSourceToEventDetector, dataPoint1, dataPoint2);
        dataPointObjectsCreator.createObjects();

        DataSourcePointObjectsCreator dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage,
                dataSourceToEventDetector, dataPoint1, dataPoint2);
        dataSourcePointObjectsCreator.createObjects();

        return dataSourceToEventDetector;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
