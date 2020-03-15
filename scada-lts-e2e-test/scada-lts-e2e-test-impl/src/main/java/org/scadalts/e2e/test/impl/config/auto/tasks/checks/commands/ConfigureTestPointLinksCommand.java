package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.identifiers.Xid;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.CreateOneDataSourceTwoPointsSubCommand;
import org.scadalts.e2e.test.impl.creators.PointLinksObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.pointlinks.ChangePointValueViaPointLinksCheckTest;

import static org.scadalts.e2e.common.utils.ExecutorUtil.executeBiFunction;

@Data
public class ConfigureTestPointLinksCommand implements Command<ChangePointValueViaPointLinksCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        executeBiFunction(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), RuntimeException::new);
    }

    private void _execute() {

        Xid dataPointSourceXid = new Xid(TestImplConfiguration.dataPointSourceXid);
        Xid dataPointTargetXid = new Xid(TestImplConfiguration.dataPointTargetXid);

        DataPointCriteria dataPointSource = DataPointCriteria.noChange(dataPointTargetXid,
                new DataPointIdentifier("datapoint_source", DataPointType.NUMERIC), "1234");
        DataPointCriteria dataPointTarget = DataPointCriteria.noChange(dataPointSourceXid,
                new DataPointIdentifier("datapoint_target", DataPointType.NUMERIC), "12345");

        DataSourceIdentifier dataSourceIdentifier = new DataSourceIdentifier("datasource_point_links_test",
                DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceCriteria dataSource = DataSourceCriteria.criteriaSecond(dataSourceIdentifier);
        DataSourcePointCriteria dataSourcePointSource = DataSourcePointCriteria.criteria(dataSource, dataPointSource);
        DataSourcePointCriteria dataSourcePointTarget = DataSourcePointCriteria.criteria(dataSource, dataPointTarget);


        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubCommand = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointSource)
                .dataPoint2(dataPointTarget)
                .dataSourceCriteria(dataSource)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceTwoPointsSubCommand.execute();

        PointLinkCriteria pointLink = PointLinkCriteria.change(dataSourcePointSource, dataSourcePointTarget);

        PointLinksObjectsCreator pointLinksObjectsCreator = new PointLinksObjectsCreator(navigationPage, pointLink);
        pointLinksObjectsCreator.createObjects();

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(DataSourceCriteria.class, dataSource);
            criteriaRegister.register(DataSourcePointCriteria.class, dataSourcePointSource);
            criteriaRegister.register(DataSourcePointCriteria.class, dataSourcePointTarget);
            criteriaRegister.register(PointLinkCriteria.class, pointLink);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Class<ChangePointValueViaPointLinksCheckTest> getClassTest() {
        return ChangePointValueViaPointLinksCheckTest.class;
    }
}
