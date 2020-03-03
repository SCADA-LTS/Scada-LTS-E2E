package org.scadalts.e2e.test.impl.config.auto.tasks;

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

@Data
public class ConfigureTestGraphicalViewsCommand implements Command<GraphicalViewsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(new GraphicalViewIdentifier(TestImplConfiguration.graphicalViewName));

        CriteriaRegister creatorObjectRegister = new CriteriaRegister();
        creatorObjectRegister.register(GraphicalViewCriteria.class, criteria);
        CriteriaRegisterAggregator creatorObjectByTestAggregator = CriteriaRegisterAggregator.INSTANCE;
        creatorObjectByTestAggregator.putRegister(getClassTarget(), creatorObjectRegister);

        GraphicalViewObjectsCreator graphicalViewObjectsCreator = new GraphicalViewObjectsCreator(navigationPage,criteria);
        graphicalViewObjectsCreator.createObjects();
    }

    @Override
    public Class<GraphicalViewsCheckTestsSuite> getClassTarget() {
        return GraphicalViewsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
