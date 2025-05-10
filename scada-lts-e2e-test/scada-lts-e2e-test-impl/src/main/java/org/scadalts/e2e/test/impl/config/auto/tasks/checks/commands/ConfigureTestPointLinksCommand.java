package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.core.criterias.Script;
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

@Data
public class ConfigureTestPointLinksCommand implements Command<ChangePointValueViaPointLinksCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);
    }

    private void _execute() {

        Xid dataPointSourceXid = new Xid(TestImplConfiguration.dataPointSourceXid);
        Xid dataPointTargetXid = new Xid(TestImplConfiguration.dataPointTargetXid);

        VirtualDataPointCriteria dataPointSource = VirtualDataPointCriteria.noChange(dataPointSourceXid,
                new DataPointIdentifier("dp_source_test", DataPointType.NUMERIC), "1234");
        VirtualDataPointCriteria dataPointTarget = VirtualDataPointCriteria.noChange(dataPointTargetXid,
                new DataPointIdentifier("dp_target_test", DataPointType.NUMERIC), "12345");

        DataSourceIdentifier dataSourceIdentifier = new DataSourceIdentifier("ds_point_links_test",
                DataSourceType.VIRTUAL_DATA_SOURCE);
        UpdateDataSourceCriteria dataSource = UpdateDataSourceCriteria.criteriaSecond(dataSourceIdentifier);
        VirtualDataSourcePointCriteria dataSourcePointSource = VirtualDataSourcePointCriteria.virtualCriteria(dataSource, dataPointSource);
        VirtualDataSourcePointCriteria dataSourcePointTarget = VirtualDataSourcePointCriteria.virtualCriteria(dataSource, dataPointTarget);


        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubCommand = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointSource)
                .dataPoint2(dataPointTarget)
                .dataSource(dataSource)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceTwoPointsSubCommand.execute();

        Script script = Script.sourceValueIncreasedOne();
        VirtualPointLinkCriteria pointLink = VirtualPointLinkCriteria.change(dataSourcePointSource, dataSourcePointTarget, script);

        PointLinksObjectsCreator pointLinksObjectsCreator = new PointLinksObjectsCreator(navigationPage, pointLink);
        pointLinksObjectsCreator.createObjects();

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(UpdateDataSourceCriteria.class, dataSource);
            criteriaRegister.register(VirtualDataSourcePointCriteria.class, dataSourcePointSource);
            criteriaRegister.register(VirtualDataSourcePointCriteria.class, dataSourcePointTarget);
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
