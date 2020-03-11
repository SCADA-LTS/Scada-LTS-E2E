package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;

@Builder
public class CreateOneDataSourceTwoPointsSubCommand implements SubCommand<DataSourceCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceCriteria dataSourceCriteria;
    private final @NonNull DataPointCriteria dataPoint1;
    private final @NonNull DataPointCriteria dataPoint2;

    private CreateOneDataSourceTwoPointsSubCommand(@NonNull NavigationPage navigationPage,
                                                   @NonNull DataSourceCriteria dataSourceCriteria,
                                                   @NonNull DataPointCriteria dataPoint1,
                                                   @NonNull DataPointCriteria dataPoint2) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPoint1 = dataPoint1;
        this.dataPoint2 = dataPoint2;
    }

    @Override
    public DataSourceCriteria execute() {

        DataSourcePointCriteria dataSourcePoint1 = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPoint1);
        DataSourcePointCriteria dataSourcePoint2 = DataSourcePointCriteria
                .criteria(dataSourceCriteria, dataPoint2);

        DataSourcePointObjectsCreator dataSourcePointObjectsCreator =
                new DataSourcePointObjectsCreator(navigationPage, dataSourcePoint1,
                        dataSourcePoint2);

        dataSourcePointObjectsCreator.createObjects();

        DataPointObjectsCreator dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage,
                dataSourceCriteria, dataPoint1, dataPoint2);
        dataPointObjectsCreator.createObjects();

        return dataSourceCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
