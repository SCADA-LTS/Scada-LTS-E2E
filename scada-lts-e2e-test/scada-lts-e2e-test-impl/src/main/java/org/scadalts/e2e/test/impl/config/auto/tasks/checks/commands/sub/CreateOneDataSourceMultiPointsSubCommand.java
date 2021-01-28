package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;

import java.util.Set;

@Builder
public class CreateOneDataSourceMultiPointsSubCommand implements SubCommand<DataSourceCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceCriteria dataSource;
    private final @NonNull @Singular Set<DataPointCriteria> dataPoints;

    @Override
    public DataSourceCriteria execute() {
        DataSourcePointObjectsCreator dataSourcePointObjectsCreator =
                new DataSourcePointObjectsCreator(navigationPage, dataSource,
                        dataPoints.toArray(new DataPointCriteria[]{}));
        dataSourcePointObjectsCreator.createObjects();

        DataPointObjectsCreator dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage,
                dataSource, dataPoints.toArray(new DataPointCriteria[]{}));
        dataPointObjectsCreator.createObjects();
        return dataSource;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
