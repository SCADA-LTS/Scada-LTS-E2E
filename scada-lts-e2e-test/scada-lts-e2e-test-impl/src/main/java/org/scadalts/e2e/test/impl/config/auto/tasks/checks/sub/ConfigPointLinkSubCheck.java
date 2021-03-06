package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.test.impl.matchers.ContainsObject.containsObject;

@Data
@Log4j2
public class ConfigPointLinkSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<PointLinkCriteria> pointLinkCriterias;

    @Override
    public void check() {
        logger.info("run... {}", this.getClass().getSimpleName());
        PointLinksPage pointLinksPage = navigationPage.openPointLinks();
        for(PointLinkCriteria pointLinkCriteria: pointLinkCriterias) {
            pointLinksPage.waitForObject(pointLinkCriteria.getIdentifier());
            assertThat(pointLinksPage, containsObject(pointLinkCriteria.getIdentifier()));
        }
    }
}
