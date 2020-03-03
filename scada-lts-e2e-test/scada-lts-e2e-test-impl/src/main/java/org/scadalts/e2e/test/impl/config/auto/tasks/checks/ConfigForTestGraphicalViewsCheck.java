package org.scadalts.e2e.test.impl.config.auto.tasks.checks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.tests.check.graphicalviews.GraphicalViewsCheckTestsSuite;

@Data
public class ConfigForTestGraphicalViewsCheck implements Check<GraphicalViewsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(new GraphicalViewIdentifier(TestImplConfiguration.graphicalViewName));
        GraphicalViewsPage graphicalViewsPage = navigationPage.openGraphicalViews();

        graphicalViewsPage.selectViewByName(criteria.getIdentifier());
        graphicalViewsPage.waitOnLoadedBackground();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Class<GraphicalViewsCheckTestsSuite> getClassTarget() {
        return GraphicalViewsCheckTestsSuite.class;
    }
}
