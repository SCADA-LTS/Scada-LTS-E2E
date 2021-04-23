package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.Set;

@Data
@Log4j2
public class ConfigGraphicalViewSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<GraphicalViewCriteria> graphicalViewCriterias;

    @Override
    public void check() {
        logger.info("run... {}", this.getClass().getSimpleName());
        for(GraphicalViewCriteria graphicalViewCriteria: graphicalViewCriterias) {
            GraphicalViewsPage graphicalViewsPage = navigationPage.openGraphicalViews();
            graphicalViewsPage.selectViewBy(graphicalViewCriteria.getIdentifier());
            graphicalViewsPage.waitOnLoadedBackground();
        }
    }
}
