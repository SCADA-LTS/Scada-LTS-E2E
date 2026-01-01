package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.dicts.EventType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
public class PointLinksObjectsCreator implements CreatorObject<PointLinksPage, PointLinksPage> {

    private NavigationPage navigationPage;
    private final PointLinkCriteria<?,?>[] pointLinkCriterias;

    @Getter
    private PointLinksPage pointLinksPage;

    public PointLinksObjectsCreator(NavigationPage navigationPage, PointLinkCriteria<?,?>... pointLinkCriterias) {
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
        for (PointLinkCriteria<?,?> criteria : pointLinkCriterias) {
            if(!pointLinksPage.containsObject(criteria.getIdentifier())) {

                logger.info("creating object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());

                pointLinksPage.openPointLinkCreator()
                        .setPoints(criteria)
                        .setScript(criteria.getScript())
                        .setEventType(criteria.getIdentifier().getType())
                        .savePointLink();

                logger.info("created object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());

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
            if (pointLinksPage.containsObject(criteria.getIdentifier())) {
                logger.info("delete object: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getXid().getValue(), criteria.getClass().getSimpleName());
                pointLinksPage.openPointLinkEditor(criteria)
                        .deletePointLink()
                        .reopen();
            }
        }
        return pointLinksPage;
    }

    public static boolean isUpdate(EventType eventType, String previousValue, String value) {
        return eventType == EventType.UPDATE || !previousValue.equals(value);
    }

    @Override
    public void reload() {
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }
}
