package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestDataPointDetailsCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigDataSourcePointSubCheck;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigWatchListSubCheck;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;

import java.util.Set;

@Data
public class ConfigForTestDataPointDetailsCheck implements Check<DataPointDetailsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        CriteriaRegister register = CriteriaRegister.getRegister(getClassTest(), new ConfigureTestDataPointDetailsCommand(navigationPage));

        Set<DataSourcePointCriteria> dataSourcePointCriterias = register.get(DataSourcePointCriteria.class);
        ConfigDataSourcePointSubCheck checkConfigDataSourcePointSubTask = new ConfigDataSourcePointSubCheck(navigationPage, dataSourcePointCriterias);
        checkConfigDataSourcePointSubTask.check();

        Set<WatchListCriteria> watchListCriterias = register.get(WatchListCriteria.class);
        ConfigWatchListSubCheck configWatchListSubCheck = new ConfigWatchListSubCheck(navigationPage, watchListCriterias);
        configWatchListSubCheck.check();
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
