package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;

import java.util.Set;

import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
public class ConfigPointLinkSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<PointLinkCriteria> pointLinkCriterias;

    @Override
    public void check() {
        PointLinksPage pointLinksPage = navigationPage.openPointLinks();
        for(PointLinkCriteria pointLinkCriteria: pointLinkCriterias) {
            assertExists(pointLinksPage, pointLinkCriteria);
        }
    }
}
