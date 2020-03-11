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
    private final PointLinkCriteria[] pointLinkCriterias;

    @Getter
    private PointLinksPage pointLinksPage;

    public PointLinksObjectsCreator(NavigationPage navigationPage, PointLinkCriteria... pointLinkCriterias) {
        this.pointLinkCriterias = pointLinkCriterias;
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
        for (PointLinkCriteria criteria : pointLinkCriterias) {
            if(!pointLinksPage.containsObject(criteria)) {
                logger.info("create object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                pointLinksPage.openPointLinkCreator()
                        .setPoints(criteria)
                        .setScript(criteria.getScript())
                        .setEventType(criteria.getType())
                        .savePointLink();
                if(pointLinkCriterias.length != 1) {
                    pointLinksPage.reopen();
                }
            }
        }

        return pointLinksPage;
    }

    @Override
    public PointLinksPage deleteObjects() {
        PointLinksPage pointLinksPage = openPage();
        for (PointLinkCriteria criteria : pointLinkCriterias) {
            if (pointLinksPage.containsObject(criteria)) {
                logger.info("delete object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                pointLinksPage.openPointLinkEditor(criteria)
                        .deletePointLink()
                        .reopen();
            }
        }
        return pointLinksPage;
    }
}
