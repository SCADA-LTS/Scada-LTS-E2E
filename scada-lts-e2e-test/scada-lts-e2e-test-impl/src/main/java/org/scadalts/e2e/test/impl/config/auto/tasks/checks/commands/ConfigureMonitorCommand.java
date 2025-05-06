package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.creators.InternalDataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.InternalDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.VirtualDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;

@Data
public class ConfigureMonitorCommand implements Command<DataPointDetailsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);

    }

    private void _execute() {
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.criteriaSecond(new DataSourceIdentifier("monitor", DataSourceType.INTERNAL_DATA_SOURCE));

        InternalDataSourcePointCriteria pointValuesToBeWritten = InternalDataSourcePointCriteria.pointValuesToBeWritten(dataSourceCriteria,"DP_986471");
        InternalDataSourcePointCriteria pointValueWriteThreads = InternalDataSourcePointCriteria.pointValueWriteThreads(dataSourceCriteria,"DP_628851");
        InternalDataSourcePointCriteria maximumThreadStackHeight = InternalDataSourcePointCriteria.maximumThreadStackHeight(dataSourceCriteria,"DP_945136");
        InternalDataSourcePointCriteria highPriorityWorkItems = InternalDataSourcePointCriteria.highPriorityWorkItems(dataSourceCriteria,"DP_800114");
        InternalDataSourcePointCriteria mediumPriorityWorkItems = InternalDataSourcePointCriteria.mediumPriorityWorkItems(dataSourceCriteria,"DP_182130");
        InternalDataSourcePointCriteria scheduledWorkItems = InternalDataSourcePointCriteria.scheduledWorkItems(dataSourceCriteria,"DP_490877");
        InternalDataSourcePointCriteria activeThreadCount = InternalDataSourcePointCriteria.activeThreadCount(dataSourceCriteria,"DP_809550");

        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(new WatchListIdentifier("monitor-wl"),
                pointValuesToBeWritten,
                pointValueWriteThreads,
                maximumThreadStackHeight,
                highPriorityWorkItems,
                mediumPriorityWorkItems,
                scheduledWorkItems,
                activeThreadCount
        );

        InternalDataSourcePointObjectsCreator dataSourcePointObjectsCreator = new InternalDataSourcePointObjectsCreator(navigationPage,
                pointValuesToBeWritten,
                pointValueWriteThreads,
                maximumThreadStackHeight,
                highPriorityWorkItems,
                mediumPriorityWorkItems,
                scheduledWorkItems,
                activeThreadCount
        );
        dataSourcePointObjectsCreator.createObjects();

        WatchListObjectsCreator watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        watchListObjectsCreator.createObjects();

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {

            criteriaRegister.register(UpdateDataSourceCriteria.class, dataSourceCriteria);

            criteriaRegister.register(InternalDataPointCriteria.class, pointValuesToBeWritten.getDataPoint());
            criteriaRegister.register(InternalDataPointCriteria.class, pointValueWriteThreads.getDataPoint());
            criteriaRegister.register(InternalDataPointCriteria.class, maximumThreadStackHeight.getDataPoint());
            criteriaRegister.register(InternalDataPointCriteria.class, highPriorityWorkItems.getDataPoint());
            criteriaRegister.register(InternalDataPointCriteria.class, mediumPriorityWorkItems.getDataPoint());
            criteriaRegister.register(InternalDataPointCriteria.class, scheduledWorkItems.getDataPoint());
            criteriaRegister.register(InternalDataPointCriteria.class, activeThreadCount.getDataPoint());

            criteriaRegister.register(InternalDataSourcePointCriteria.class, pointValuesToBeWritten);
            criteriaRegister.register(InternalDataSourcePointCriteria.class, pointValueWriteThreads);
            criteriaRegister.register(InternalDataSourcePointCriteria.class, maximumThreadStackHeight);
            criteriaRegister.register(InternalDataSourcePointCriteria.class, highPriorityWorkItems);
            criteriaRegister.register(InternalDataSourcePointCriteria.class, mediumPriorityWorkItems);
            criteriaRegister.register(InternalDataSourcePointCriteria.class, scheduledWorkItems);
            criteriaRegister.register(InternalDataSourcePointCriteria.class, activeThreadCount);

            criteriaRegister.register(WatchListCriteria.class, watchListCriteria);

        }
    }

    @Override
    public Class<DataPointDetailsCheckTestsSuite> getClassTest() {
        return DataPointDetailsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
