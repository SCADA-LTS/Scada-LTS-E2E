package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.GraphicalViewsCheckTestsSuite;

import static org.scadalts.e2e.common.utils.ExecutorUtil.executeBiFunction;

@Data
public class ConfigureTestGraphicalViewsCommand implements Command<GraphicalViewsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        executeBiFunction(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), RuntimeException::new);
    }

    private void _execute() {

        GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(new GraphicalViewIdentifier(TestImplConfiguration.graphicalViewName));

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(GraphicalViewCriteria.class, criteria);
        }
        GraphicalViewObjectsCreator graphicalViewObjectsCreator = new GraphicalViewObjectsCreator(navigationPage, criteria);
        graphicalViewObjectsCreator.createObjects();
    }

    @Override
    public Class<GraphicalViewsCheckTestsSuite> getClassTest() {
        return GraphicalViewsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
