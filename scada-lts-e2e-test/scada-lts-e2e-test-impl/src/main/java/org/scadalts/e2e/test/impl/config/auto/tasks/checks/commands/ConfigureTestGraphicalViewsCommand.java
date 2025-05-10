package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.GraphicalViewsCheckTestsSuite;

@Data
public class ConfigureTestGraphicalViewsCommand implements Command<GraphicalViewsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);
    }

    private void _execute() {

        GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(new GraphicalViewIdentifier(TestImplConfiguration.graphicalViewName));

        GraphicalViewObjectsCreator graphicalViewObjectsCreator = new GraphicalViewObjectsCreator(navigationPage, criteria);
        graphicalViewObjectsCreator.createObjects();

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(GraphicalViewCriteria.class, criteria);
        }
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
