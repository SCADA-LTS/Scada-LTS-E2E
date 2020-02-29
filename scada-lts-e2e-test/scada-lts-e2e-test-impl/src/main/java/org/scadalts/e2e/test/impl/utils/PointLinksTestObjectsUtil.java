package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;

@Log4j2
public class PointLinksTestObjectsUtil implements TestObjectsUtilible<PointLinksPage, PointLinksPage> {

    private final NavigationPage navigationPage;
    private final PointLinkCriteria pointLinkCriteria;

    @Getter
    private PointLinksPage pointLinksPage;

    public PointLinksTestObjectsUtil(NavigationPage navigationPage, PointLinkCriteria pointLinkCriteria) {
        this.pointLinkCriteria = pointLinkCriteria;
        this.navigationPage = navigationPage;
    }

    @Override
    public PointLinksPage openPage() {
        if(pointLinksPage == null) {
            pointLinksPage = navigationPage.openPointLinks();
            return pointLinksPage;
        }
        return pointLinksPage.reopen();
    }

    @Override
    public PointLinksPage createObjects() {
        return openPage().openPointLinkCreator()
                .setPoints(pointLinkCriteria)
                .setScript(pointLinkCriteria.getScript())
                .setEventType(pointLinkCriteria.getType())
                .savePointLink();
    }

    @Override
    public PointLinksPage deleteObjects() {
        PointLinksPage pointLinksPage = openPage();
        if(pointLinksPage.containsObject(pointLinkCriteria)) {
            pointLinksPage.openPointLinkEditor(pointLinkCriteria)
                    .deletePointLink();
        }
        return pointLinksPage;
    }
}
