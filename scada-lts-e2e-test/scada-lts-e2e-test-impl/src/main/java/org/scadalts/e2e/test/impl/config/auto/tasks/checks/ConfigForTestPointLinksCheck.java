package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.ConfigureTestPointLinksCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigDataSourcePointSubCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigPointLinkSubCheck;
import org.scadalts.e2e.test.impl.tests.check.pointlinks.ChangePointValueViaPointLinksCheckTest;

import java.util.Set;

@Data
public class ConfigForTestPointLinksCheck implements Check<ChangePointValueViaPointLinksCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        CriteriaRegisterAggregator creatorObjectByTestAggregator = CriteriaRegisterAggregator.INSTANCE;
        if(!creatorObjectByTestAggregator.containsRegister(getClassTarget())) {
            ConfigureTestPointLinksCommand configureTestPointLinksCommand = new ConfigureTestPointLinksCommand(navigationPage);
            configureTestPointLinksCommand.execute();
        }
        CriteriaRegister register = creatorObjectByTestAggregator.getRegister(getClassTarget());

        Set<DataSourcePointCriteria> dataSourcePointCriterias = register.get(DataSourcePointCriteria.class);
        for (DataSourcePointCriteria dataSourcePointCriteria : dataSourcePointCriterias) {
            ConfigDataSourcePointSubCheck checkConfigDataSourcePointSubTask = new ConfigDataSourcePointSubCheck(navigationPage,
                    dataSourcePointCriteria);
            checkConfigDataSourcePointSubTask.execute();
        }

        Set<PointLinkCriteria> pointLinkCriterias = register.get(PointLinkCriteria.class);
        for (PointLinkCriteria pointLinkCriteria : pointLinkCriterias) {
            ConfigPointLinkSubCheck checkPointLinkSubTask = new ConfigPointLinkSubCheck(navigationPage, pointLinkCriteria);
            checkPointLinkSubTask.execute();
        }
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
