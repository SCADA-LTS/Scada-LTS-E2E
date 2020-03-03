package org.scadalts.e2e.test.impl.config.auto.tasks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.sub.CreateOneDataSourceTwoPointsSubCommand;
import org.scadalts.e2e.test.impl.creators.PointLinksObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.pointlinks.ChangePointValueViaPointLinksCheckTest;

@Data
public class ConfigureTestPointLinksCommand implements Command<ChangePointValueViaPointLinksCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        Xid dataPointSourceXid = new Xid(TestImplConfiguration.dataPointSourceXid);
        Xid dataPointTargetXid = new Xid(TestImplConfiguration.dataPointTargetXid);

        DataPointCriteria dataPointSource = DataPointCriteria.numericNoChange(dataPointTargetXid,
                new DataPointIdentifier("datapoint_source"), 1234);
        DataPointCriteria dataPointTarget = DataPointCriteria.numericNoChange(dataPointSourceXid,
                new DataPointIdentifier("datapoint_target"), 12345);

        DataSourceIdentifier identifierForPointLinks = new DataSourceIdentifier("datasource_point_links_test");

        DataSourceCriteria dataSource = DataSourceCriteria.virtualDataSourceSecond(identifierForPointLinks);
        DataSourcePointCriteria dataSourcePointSource = DataSourcePointCriteria.criteria(dataSource, dataPointSource);
        DataSourcePointCriteria dataSourcePointTarget = DataSourcePointCriteria.criteria(dataSource, dataPointTarget);

        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubTask = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointSource)
                .dataPoint2(dataPointTarget)
                .dataSourceIdentifier(identifierForPointLinks)
                .navigationPage(navigationPage)
                .build();

        createOneDataSourceTwoPointsSubTask.execute();

        PointLinkCriteria pointLinkCriteria = PointLinkCriteria.change(dataSourcePointSource, dataSourcePointTarget);

        PointLinksObjectsCreator pointLinksObjectsCreator = new PointLinksObjectsCreator(navigationPage, pointLinkCriteria);
        pointLinksObjectsCreator.createObjects();

        CriteriaRegister creatorObjectRegister = new CriteriaRegister();
        creatorObjectRegister.register(DataSourceCriteria.class, dataSource);
        creatorObjectRegister.register(DataSourcePointCriteria.class, dataSourcePointSource);
        creatorObjectRegister.register(DataSourcePointCriteria.class, dataSourcePointTarget);
        creatorObjectRegister.register(PointLinkCriteria.class, pointLinkCriteria);

        CriteriaRegisterAggregator creatorObjectByTestAggregator = CriteriaRegisterAggregator.INSTANCE;
        creatorObjectByTestAggregator.putRegister(getClassTarget(), creatorObjectRegister);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Class<ChangePointValueViaPointLinksCheckTest> getClassTarget() {
        return ChangePointValueViaPointLinksCheckTest.class;
    }
}
