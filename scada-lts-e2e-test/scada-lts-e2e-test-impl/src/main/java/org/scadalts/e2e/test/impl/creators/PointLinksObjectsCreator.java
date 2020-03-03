package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class PointLinksObjectsCreator implements CreatorObject<PointLinksPage, PointLinksPage> {

    private final NavigationPage navigationPage;
    private final PointLinkCriteria pointLinkCriteria;

    @Getter
    private PointLinksPage pointLinksPage;

    public PointLinksObjectsCreator(NavigationPage navigationPage, PointLinkCriteria pointLinkCriteria) {
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
        PointLinksPage pointLinksPage = openPage();
        if(!pointLinksPage.containsObject(pointLinkCriteria)) {
            return openPage().openPointLinkCreator()
                    .setPoints(pointLinkCriteria)
                    .setScript(pointLinkCriteria.getScript())
                    .setEventType(pointLinkCriteria.getType())
                    .savePointLink();
        }
        return pointLinksPage;
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
