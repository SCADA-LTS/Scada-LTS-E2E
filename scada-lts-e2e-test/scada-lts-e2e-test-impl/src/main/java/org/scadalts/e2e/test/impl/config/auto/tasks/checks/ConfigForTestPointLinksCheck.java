package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestPointLinksCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigDataSourcePointSubCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigPointLinkSubCheck;
import org.scadalts.e2e.test.impl.tests.check.pointlinks.ChangePointValueViaPointLinksCheckTest;

import java.util.Set;

@Data
public class ConfigForTestPointLinksCheck implements Check<ChangePointValueViaPointLinksCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        CriteriaRegister register = CriteriaRegister.getRegister(getClassTest(), new ConfigureTestPointLinksCommand(navigationPage));

        Set<VirtualDataSourcePointCriteria> dataSourcePointCriterias = register.get(VirtualDataSourcePointCriteria.class);
        ConfigDataSourcePointSubCheck checkConfigDataSourcePointSubTask = new ConfigDataSourcePointSubCheck(navigationPage, dataSourcePointCriterias);
        checkConfigDataSourcePointSubTask.check();

        Set<PointLinkCriteria> pointLinkCriterias = register.get(PointLinkCriteria.class);
        ConfigPointLinkSubCheck checkPointLinkSubCheck = new ConfigPointLinkSubCheck(navigationPage, pointLinkCriterias);
        checkPointLinkSubCheck.check();
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
