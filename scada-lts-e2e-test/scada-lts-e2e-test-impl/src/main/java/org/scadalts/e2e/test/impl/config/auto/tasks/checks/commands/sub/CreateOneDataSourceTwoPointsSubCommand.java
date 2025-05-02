package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.VirtualDataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;

@Builder
public class CreateOneDataSourceTwoPointsSubCommand implements SubCommand<UpdateDataSourceCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull UpdateDataSourceCriteria dataSource;
    private final @NonNull VirtualDataPointCriteria dataPoint1;
    private final @NonNull VirtualDataPointCriteria dataPoint2;

    private CreateOneDataSourceTwoPointsSubCommand(@NonNull NavigationPage navigationPage,
                                                   @NonNull UpdateDataSourceCriteria dataSource,
                                                   @NonNull VirtualDataPointCriteria dataPoint1,
                                                   @NonNull VirtualDataPointCriteria dataPoint2) {
        this.navigationPage = navigationPage;
        this.dataSource = dataSource;
        this.dataPoint1 = dataPoint1;
        this.dataPoint2 = dataPoint2;
    }

    @Override
    public UpdateDataSourceCriteria execute() {

        VirtualDataSourcePointCriteria dataSourcePoint1 = VirtualDataSourcePointCriteria
                .virtualCriteria(dataSource, dataPoint1);
        VirtualDataSourcePointCriteria dataSourcePoint2 = VirtualDataSourcePointCriteria
                .virtualCriteria(dataSource, dataPoint2);

        VirtualDataSourcePointObjectsCreator dataSourcePointObjectsCreator =
                new VirtualDataSourcePointObjectsCreator(navigationPage, dataSourcePoint1,
                        dataSourcePoint2);

        dataSourcePointObjectsCreator.createObjects();

        VirtualDataPointObjectsCreator dataPointObjectsCreator = new VirtualDataPointObjectsCreator(navigationPage,
                dataSource, dataPoint1, dataPoint2);
        dataPointObjectsCreator.createObjects();

        return dataSource;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
