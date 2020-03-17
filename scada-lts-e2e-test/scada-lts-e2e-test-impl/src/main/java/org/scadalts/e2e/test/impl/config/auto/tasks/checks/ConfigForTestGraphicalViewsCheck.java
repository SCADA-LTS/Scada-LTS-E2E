package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.ConfigureTestGraphicalViewsCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub.ConfigGraphicalViewSubCheck;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.GraphicalViewsCheckTestsSuite;

import java.util.Set;

@Data
public class ConfigForTestGraphicalViewsCheck implements Check<GraphicalViewsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        CriteriaRegister register = CriteriaRegister.getRegister(getClassTest(), new ConfigureTestGraphicalViewsCommand(navigationPage));
        Set<GraphicalViewCriteria> graphicalViewCriterias = register.get(GraphicalViewCriteria.class);

        ConfigGraphicalViewSubCheck configGraphicalViewSubCheck = new ConfigGraphicalViewSubCheck(navigationPage, graphicalViewCriterias);
        configGraphicalViewSubCheck.check();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Class<GraphicalViewsCheckTestsSuite> getClassTest() {
        return GraphicalViewsCheckTestsSuite.class;
    }
}
