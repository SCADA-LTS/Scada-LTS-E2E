package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.VirtualDataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;

import java.util.Set;

@Builder
public class CreateOneDataSourceMultiPointsSubCommand implements SubCommand<UpdateDataSourceCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull UpdateDataSourceCriteria dataSource;
    private final @NonNull @Singular Set<VirtualDataPointCriteria> dataPoints;

    @Override
    public UpdateDataSourceCriteria execute() {
        VirtualDataSourcePointObjectsCreator dataSourcePointObjectsCreator =
                new VirtualDataSourcePointObjectsCreator(navigationPage, dataSource,
                        dataPoints.toArray(new VirtualDataPointCriteria[]{}));
        dataSourcePointObjectsCreator.createObjects();

        VirtualDataPointObjectsCreator dataPointObjectsCreator = new VirtualDataPointObjectsCreator(navigationPage,
                dataSource, dataPoints.toArray(new VirtualDataPointCriteria[]{}));
        dataPointObjectsCreator.createObjects();
        return dataSource;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
