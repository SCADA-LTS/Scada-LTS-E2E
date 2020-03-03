package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.core.asserts.E2eAssert;

@Data
public class ConfigPointLinkSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull PointLinkCriteria pointLinkCriteria;

    @Override
    public void execute() {
        PointLinksPage pointLinksPage = navigationPage.openPointLinks();
        E2eAssert.assertExists(pointLinksPage, pointLinkCriteria);
    }
}
